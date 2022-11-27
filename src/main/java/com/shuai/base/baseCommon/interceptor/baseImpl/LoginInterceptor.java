package com.shuai.base.baseCommon.interceptor.baseImpl;

import com.alibaba.fastjson.JSON;
import com.shuai.base.baseCommon.annotation.basePermission.BaseInterceptorService;
import com.shuai.base.baseCommon.common.ExecuteStatus;
import com.shuai.base.baseCommon.common.User;
import com.shuai.base.baseCommon.common.UserPermission;
import com.shuai.base.baseCommon.common.UserUtils;
import com.shuai.base.baseCommon.interceptor.BaseInterceptor;
import com.shuai.base.baseCommon.interceptor.LoginBaseInterceptor;
import com.shuai.base.baseCommon.message.ResponseMessage;
import com.shuai.base.baseCommon.permission.Authentication;
import com.shuai.base.utils.MessageUtils;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/18 11:56
 * @version: 1.0
 */

@Order(2)
@ControllerAdvice
@Log4j2
@NoArgsConstructor
@BaseInterceptorService(excludePathPatterns = {"/static/**"})
public class LoginInterceptor extends BaseInterceptor implements ResponseBodyAdvice<Object>, LoginBaseInterceptor {
    @Autowired
    public Authentication authentication;

    public LoginInterceptor(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (uri.endsWith("exit")) {
            request.getSession().invalidate();
            response.getWriter().write(JSON.toJSONString(MessageUtils.exit()));
            response.sendRedirect("/static/html/JDindex.html");
            return false;
        }
        return authentication.inspectAuthentication(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Object o = request.getSession().getAttribute("user");
        if (o instanceof User) {
            User user = (User) o;
            user.setLastActivityTime(System.currentTimeMillis());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getMethod().getName().endsWith("login");
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof ResponseMessage) {
            ResponseMessage bodyMsg = (ResponseMessage) body;
            if (bodyMsg.getStatus() == ExecuteStatus.STATUS_LOGIN_OK) {
                User user = (User) bodyMsg.getData();
                if (UserUtils.isCanLoginIn(user.getPermission())) {
                    ServletServerHttpResponse rsp = (ServletServerHttpResponse) response;
                    ServletServerHttpRequest rqt = (ServletServerHttpRequest) request;
                    rqt.getServletRequest().getSession().setAttribute("user", user);
                    String requestID = UserUtils.getUserRequestId();
                    user.setRequestId(requestID);
                    String cookieId = UserUtils.getCooKieByID(requestID);
                    Cookie cookie = new Cookie("requestID", cookieId);
                    cookie.setMaxAge(UserPermission.DEFAULT_TIMEOUT_S);
                    rsp.getServletResponse().addCookie(cookie);
                    user.setEffectiveTime(UserPermission.DEFAULT_TIMEOUT_MS);
                    user.setLoginStatus(UserPermission.USER_LOGGED);
                }
            }
        }
        return body;
    }
}
