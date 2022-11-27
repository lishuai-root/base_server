package com.shuai.base.baseCommon.interceptor.baseImpl;

import com.shuai.base.baseCommon.annotation.basePermission.BaseInterceptorService;
import com.shuai.base.baseCommon.interceptor.BaseInterceptor;
import com.shuai.base.baseCommon.interceptor.PermissionBaseInterceptor;
import com.shuai.base.baseCommon.permission.Permission;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/18 16:15
 * @version: 1.0
 */

@NoArgsConstructor
@BaseInterceptorService(excludePathPatterns = {"/static/**", "/base/login/**"}, order = 4)
public class PermissionInterceptor extends BaseInterceptor implements PermissionBaseInterceptor {

    @Autowired
    Permission permission;

    public PermissionInterceptor(Permission authentication) {
        this.permission = authentication;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return permission.inspectPermission(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
