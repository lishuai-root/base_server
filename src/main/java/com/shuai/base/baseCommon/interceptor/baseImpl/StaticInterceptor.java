package com.shuai.base.baseCommon.interceptor.baseImpl;

import com.shuai.base.baseCommon.annotation.basePermission.BaseInterceptorService;
import com.shuai.base.baseCommon.interceptor.BaseInterceptor;
import com.shuai.base.baseCommon.interceptor.StaticBaseInterceptor;
import com.shuai.base.baseCommon.permission.StaticResourceStrategy;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/18 11:56
 * @version: 1.0
 */

@NoArgsConstructor
@BaseInterceptorService(addPathPatterns = {"/static/**"}, order = 6)
public class StaticInterceptor extends BaseInterceptor implements StaticBaseInterceptor {

    @Autowired
    StaticResourceStrategy staticResourceStrategy;

    public StaticInterceptor(StaticResourceStrategy staticResourceStrategy) {
        this.staticResourceStrategy = staticResourceStrategy;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return staticResourceStrategy.staticResourceStrategy(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
