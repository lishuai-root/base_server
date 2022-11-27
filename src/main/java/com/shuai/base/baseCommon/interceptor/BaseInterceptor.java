package com.shuai.base.baseCommon.interceptor;

import com.shuai.base.baseCommon.annotation.basePermission.BaseIgnoreInterception;
import com.shuai.base.baseCommon.annotation.basePermission.BaseProxy;
import com.shuai.base.baseCommon.common.User;
import com.shuai.base.baseCommon.common.UserPermission;
import com.shuai.base.baseCommon.common.UserUtils;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/19 21:40
 * @version: 1.0
 */

@Component
public abstract class BaseInterceptor implements HandlerInterceptor {

    /**
     * 缓存可以忽略拦截的接口
     */
    SoftReference<ConcurrentHashMap<Object, BaseIgnoreInterception>> baseIgnoreInterceptors;


    @BaseProxy(method = "isIgnorePreInterceptor",
            parameter = {HttpServletRequest.class, HttpServletResponse.class, Object.class},
            expect = false, returnExp = false
    )
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @BaseProxy(method = "isIgnorePostInterceptor",
            parameter = {HttpServletRequest.class, HttpServletResponse.class, Object.class},
            expect = false, returnExp = false
    )
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @BaseProxy(method = "isIgnoreAfterCompletion",
            parameter = {HttpServletRequest.class, HttpServletResponse.class, Object.class},
            expect = false, returnExp = false
    )
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }


    /**
     * 获取可以忽略的拦截器接口缓存
     *
     * @param baseIgnoreInterceptors
     * @return
     */
    final SoftReference<ConcurrentHashMap<Object, BaseIgnoreInterception>> getBaseIgnoreInterceptors(SoftReference<ConcurrentHashMap<Object, BaseIgnoreInterception>> baseIgnoreInterceptors) {
        if (baseIgnoreInterceptors.get() == null) {
            synchronized (BaseInterceptor.class) {
                if (baseIgnoreInterceptors.get() == null) {
                    baseIgnoreInterceptors.enqueue();
                    baseIgnoreInterceptors = new SoftReference<>(new ConcurrentHashMap<>());
                }
            }
        }
        return baseIgnoreInterceptors;
    }

    /**
     * 获取缓存
     *
     * @return
     */
    final ConcurrentHashMap<Object, BaseIgnoreInterception> getIgnoreInterceptorsCache() {
        return (baseIgnoreInterceptors = getBaseIgnoreInterceptors(baseIgnoreInterceptors)).get();
    }

    final BaseIgnoreInterception getIgnoreBean(Object handler) {
        BaseIgnoreInterception annotation = null;
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Object bean = handlerMethod.getBean();
            ConcurrentHashMap<Object, BaseIgnoreInterception> ignoreInterceptorsCache = getIgnoreInterceptorsCache();
            if (!ignoreInterceptorsCache.containsKey(bean)) {
                ignoreInterceptorsCache.put(bean, bean.getClass().getAnnotation(BaseIgnoreInterception.class));
            }
            annotation = ignoreInterceptorsCache.get(bean);
        }
        return annotation;
    }

    final BaseIgnoreInterception getIgnoreMethod(Object handler) {
        BaseIgnoreInterception annotation = null;
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            ConcurrentHashMap<Object, BaseIgnoreInterception> ignoreInterceptorsCache = getIgnoreInterceptorsCache();
            if (!ignoreInterceptorsCache.containsKey(method)) {
                ignoreInterceptorsCache.put(method, method.getAnnotation(BaseIgnoreInterception.class));
            }
            annotation = ignoreInterceptorsCache.get(method);
        }
        return annotation;
    }


    /**
     * 是否跳过后置处理
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    protected boolean isIgnoreAfterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler) {
        BaseIgnoreInterception annotation = getIgnoreInterception(handler);
        boolean isIgnore = false;
        if (annotation != null && annotation.ignoreAfterCompletion()) {
            isIgnore = isIgnoreInterceptor(request, response, annotation);
        }
        return isIgnore;
    }

    /**
     * 是否跳过后置拦截
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    protected boolean isIgnorePostInterceptor(HttpServletRequest request, HttpServletResponse response, Object handler) {
        BaseIgnoreInterception annotation = getIgnoreInterception(handler);
        boolean isIgnore = false;
        if (annotation != null && annotation.ignorePostHandle()) {
            isIgnore = isIgnoreInterceptor(request, response, annotation);
        }
        return isIgnore;
    }

    /**
     * 是否跳过当前拦截器
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    protected boolean isIgnorePreInterceptor(HttpServletRequest request, HttpServletResponse response, Object handler) {
        BaseIgnoreInterception annotation = getIgnoreInterception(handler);
        boolean isIgnore = false;
        if (annotation != null && annotation.ignorePreHandle()) {
            isIgnore = isIgnoreInterceptor(request, response, annotation);
        }
        return isIgnore;
    }

    /**
     * 获取{@BaseIgnoreInterception}
     *
     * @param handler
     * @return
     */
    protected BaseIgnoreInterception getIgnoreInterception(Object handler) {
        BaseIgnoreInterception annotation = null;
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            BaseIgnoreInterception beanAnnotation = handlerMethod.getBeanType().getAnnotation(BaseIgnoreInterception.class);
            BaseIgnoreInterception methodAnnotation = handlerMethod.getMethodAnnotation(BaseIgnoreInterception.class);
            annotation = methodAnnotation == null ? beanAnnotation : methodAnnotation;
        }
        return annotation;
    }

    /**
     * 检查用户是否可以跳过当前拦截器
     *
     * @param request
     * @param response
     * @param annotation
     * @return
     */
    protected boolean isIgnoreInterceptor(HttpServletRequest request, HttpServletResponse response, @NotNull BaseIgnoreInterception annotation) {
        boolean isIgnore = false, hasPermission;

        Object o = request.getSession().getAttribute("user");
        if (o instanceof User) {
            User user = (User) o;
            hasPermission = UserUtils.hasPermission(user.getPermission(), annotation.ignorePermission());
        } else {
            hasPermission = UserUtils.hasPermission(UserPermission.DEFAULT_PERMISSION, annotation.ignorePermission());
        }
        if (hasPermission) {
            isIgnore = isIgnoreInterceptor(annotation);
        }
        return isIgnore;
    }

    /**
     * 检查是否可以跳过当前拦截器
     *
     * @param annotation
     * @return
     */
    protected boolean isIgnoreInterceptor(BaseIgnoreInterception annotation) {
        boolean isIgnore = false;
        Class<?> zClass = this.getClass();
        Class<?>[] classes = annotation.baseIgnoreClass();
        if (annotation.relax()) {
            for (Class<?> cl : classes) {
                if (cl.isAssignableFrom(zClass)) {
                    isIgnore = true;
                    break;
                }
            }
        } else {
            for (Class<?> cl : classes) {
                if (zClass.equals(cl)) {
                    isIgnore = true;
                    break;
                }
            }
        }
        return isIgnore;
    }
}
