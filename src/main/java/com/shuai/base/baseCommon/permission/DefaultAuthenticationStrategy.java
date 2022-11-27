package com.shuai.base.baseCommon.permission;

import com.alibaba.fastjson.JSON;
import com.shuai.base.baseCommon.annotation.basePermission.BaseRequestPermission;
import com.shuai.base.baseCommon.common.User;
import com.shuai.base.baseCommon.common.UserPermission;
import com.shuai.base.baseCommon.common.UserUtils;
import com.shuai.base.utils.MessageUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/18 13:11
 * @version: 1.0
 */

public abstract class DefaultAuthenticationStrategy implements Permission, Authentication, NetworkSecurity, StaticResourceStrategy {

    private static final String LOGIN = "login";

    @Override
    public boolean inspectAuthentication(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        inspectBeforeProcessor(request, response, handler);
        boolean authenticated = doInspectAuthentication(request, response, handler);
        authenticated = inspectPostProcessor(request, response, handler, authenticated);
        String uri = request.getRequestURI();
        if (uri.endsWith(LOGIN)) {
            if (authenticated) {
                response.getWriter().write(JSON.toJSONString(MessageUtils.login()));
            }
            authenticated = !authenticated;
        } else {
            if (!authenticated) {
                response.sendRedirect("/static/html/JDindex.html");
            }
        }
        return authenticated;
    }

    protected boolean doInspectAuthentication(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return isExistsUser(request);
    }


    @Override
    public boolean inspectPermission(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        inspectBeforeProcessor(request, response, handler);
        boolean permitted = doInspectPermission(request, response, handler);
        permitted = inspectPostProcessor(request, response, handler, permitted);
        if (!permitted) {
            response.getWriter().write(JSON.toJSONString(MessageUtils.noAuthority()));
        }
        return permitted;
    }


    protected boolean doInspectPermission(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean permitted = true;
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            BaseRequestPermission beanAnnotation = handlerMethod.getBeanType().getAnnotation(BaseRequestPermission.class);
            BaseRequestPermission methodAnnotation = handlerMethod.getMethodAnnotation(BaseRequestPermission.class);
            Object o = request.getSession().getAttribute("user");
            int userPermission = UserPermission.DEFAULT_PERMISSION;
            if (o instanceof User) {
                User user = (User) o;
                userPermission = user.getPermission();
            }
            if (methodAnnotation != null) {
                permitted = UserUtils.hasPermission(userPermission, methodAnnotation.baseExecutePermission());
            } else if (beanAnnotation != null) {
                permitted = UserUtils.hasPermission(userPermission, beanAnnotation.baseExecutePermission());
            }
        }
        return permitted;
    }


    @Override
    public boolean inspectNetworkSecurity(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        inspectBeforeProcessor(request, response, handler);
        boolean networkSecurity = doInspectNetworkSecurity(request, response, handler);

        return inspectPostProcessor(request, response, handler, networkSecurity);
    }


    protected boolean doInspectNetworkSecurity(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }


    @Override
    public boolean staticResourceStrategy(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        inspectBeforeProcessor(request, response, handler);
        boolean staticResource = doStaticResourceStrategy(request, response, handler);

        return inspectPostProcessor(request, response, handler, staticResource);
    }

    protected boolean doStaticResourceStrategy(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }

    /**
     * 前置处理器
     *
     * @param request
     * @param response
     * @param handler
     */
    protected void inspectBeforeProcessor(HttpServletRequest request, HttpServletResponse response, Object handler) {
    }

    /**
     * 后置处理器
     *
     * @param request
     * @param response
     * @param handler
     * @param authenticated
     * @return
     */
    protected boolean inspectPostProcessor(HttpServletRequest request, HttpServletResponse response, Object handler, boolean authenticated) {
        return authenticated;
    }


    protected boolean isExistsUser(HttpServletRequest request) {
        Object o = request.getSession().getAttribute("user");

        if (o instanceof User) {
            User user = (User) o;
            if (!UserUtils.isCanLoginIn(user.getPermission()) || UserUtils.checkOutTime(user)) {
                return false;
            }
            Cookie[] cookies = request.getCookies();
            for (Cookie c : cookies) {
                if ("requestID".equals(c.getName())) {
                    String cid = c.getValue();
                    if (UserUtils.checkCookidByID(cid, user.getRequestId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
