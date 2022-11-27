package com.shuai.base.baseCommon.baseProxy.impl;

import com.shuai.base.baseCommon.annotation.basePermission.BaseProxy;
import com.shuai.base.baseCommon.baseProxy.Handler;
import com.shuai.base.utils.ClassUtils;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/21 9:11
 * @version: 1.0
 */

public class BaseInterceptorProxy<T> implements Handler, MethodInterceptor {

    private Class<T> baseProxy;

    public BaseInterceptorProxy(Class<T> clazz) {
        baseProxy = clazz;
    }

    public BaseInterceptorProxy() {
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object bs = true;
        boolean po = false;
        BaseProxy annotation = null;
        if (this.baseProxy.isAssignableFrom(o.getClass())) {
            Method proxyMethod = ClassUtils.getAnnotationMethod(o.getClass(), method.getName(), method.getParameterTypes(), BaseProxy.class);
            if (proxyMethod != null) {
                annotation = proxyMethod.getAnnotation(BaseProxy.class);
                bs = annotation.defaultValue();
                Method beforeMethod = ClassUtils.getDeclaredMethod(o.getClass(), annotation.method(), annotation.parameter());
                boolean b = beforeMethod.canAccess(o);
                if (!b) {
                    if (annotation.accessible()) {
                        beforeMethod.setAccessible(annotation.accessible());
                    }
                }
                if (beforeMethod.canAccess(o)) {
                    po = (boolean) invokeMethod(o, beforeMethod, objects);
                }
                if (annotation.returnExp()) {
                    bs = po;
                }
            }
        }
        if (annotation == null || po == annotation.expect()) {
            if (!method.canAccess(o)) {
                method.setAccessible(true);
            }
            if (method.getReturnType().equals(void.class)) {
                methodProxy.invokeSuper(o, objects);
            } else {
                bs = methodProxy.invokeSuper(o, objects);
            }
        }
        return bs;
    }


    private Object invokeMethod(Object o, Method method, Object[] objects) throws InvocationTargetException, IllegalAccessException {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length > objects.length) {
            return true;
        }
        Object[] parameters = new Object[parameterTypes.length];
        int index = 0;
        for (int i = 0; i < parameterTypes.length; i++) {
            while (index < objects.length && !parameterTypes[i].isAssignableFrom(objects[index].getClass())) {
                index++;
            }
            if (index >= objects.length) {
                return true;
            }
            parameters[i] = objects[index++];
        }
        return method.invoke(o, parameters);
    }
}
