package com.shuai.base.baseCommon.interceptor.baseImpl;

import com.shuai.base.baseCommon.annotation.basePermission.BaseInterceptorService;
import com.shuai.base.baseCommon.interceptor.BaseInterceptor;
import com.shuai.base.baseCommon.interceptor.NetworkBaseInterceptor;
import com.shuai.base.baseCommon.permission.NetworkSecurity;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/19 14:07
 * @version: 1.0
 */

@NoArgsConstructor
@BaseInterceptorService(order = 0)
public class NetworkSecurityInterceptor extends BaseInterceptor implements NetworkBaseInterceptor {
    @Autowired
    NetworkSecurity networkSecurity;

    public NetworkSecurityInterceptor(NetworkSecurity networkSecurity) {
        this.networkSecurity = networkSecurity;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return networkSecurity.inspectNetworkSecurity(request, response, handler);
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
