package com.shuai.base.baseCommon.baseProxy.impl;

import com.shuai.base.baseCommon.annotation.basePermission.BaseProxy;
import com.shuai.base.baseCommon.baseProxy.BaseAssertProxy;
import com.shuai.base.baseCommon.interceptor.BaseInterceptor;
import com.shuai.base.baseCommon.interceptor.baseImpl.LoginInterceptor;
import com.shuai.base.baseCommon.permission.BasePermission;
import com.shuai.base.baseCommon.permission.Permission;
import com.shuai.base.baseCommon.permission.impl.CurrencyAuthenticationStrategy;
import com.shuai.base.utils.ClassUtils;
import lombok.SneakyThrows;
import org.springframework.cglib.proxy.CallbackHelper;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/20 23:42
 * @version: 1.0
 */

public class ParseAssertProxy {

    public static void main(String[] args) {
        LoginInterceptor proxy = (LoginInterceptor) createProxy(new LoginInterceptor(), new CurrencyAuthenticationStrategy());
        System.out.println(proxy);
        System.out.println(proxy.authentication);
    }

    public static Object createProxy(BaseInterceptor o, Permission param) {
        return new ParseAssertProxyEntry().createProxy(o, param);
    }

    public static Object createProxy(Class<?> clazz) {
        return new ParseAssertProxyEntry().createProxy(clazz);
    }

    private static class ParseAssertProxyEntry implements BaseAssertProxy {
        private Class<?> interceptor;

        @Override
        public Object createProxy(Class<?> clazz) {
            interceptor = clazz;
            Enhancer enhancer = new Enhancer();
            BaseCallbackHelper helper = new BaseCallbackHelper(clazz, new Class[]{clazz});
            enhancer.setSuperclass(clazz);
            enhancer.setCallbacks(helper.getCallbacks());
            enhancer.setCallbackFilter(helper);
            enhancer.setAttemptLoad(true);
            return enhancer.create();
        }

        public Object createProxy(BaseInterceptor o, Permission param) {
            interceptor = o.getClass();
            Enhancer enhancer = new Enhancer();
            BaseCallbackHelper helper = new BaseCallbackHelper(o.getClass(), new Class[]{o.getClass()});
            enhancer.setSuperclass(o.getClass());
            enhancer.setCallbacks(helper.getCallbacks());
            enhancer.setCallbackFilter(helper);
            enhancer.setAttemptLoad(true);

            Object o1 = null;
            Class<?> cl = null;
            Constructor<?>[] constructors = o.getClass().getConstructors();
            for (Constructor constructor : constructors) {
                if (constructor.getParameterCount() == 1) {
                    Class[] parameterTypes = constructor.getParameterTypes();
                    if (parameterTypes[0].isAssignableFrom(param.getClass())) {
                        cl = parameterTypes[0];
                    }
                }
            }
            if (cl == null) {
                o1 = enhancer.create();
            } else {
                o1 = enhancer.create(new Class[]{cl}, new Object[]{param});
            }
            Field[] declaredFields = o.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                if (BasePermission.class.isAssignableFrom(field.getType())) {
                    try {
                        field.setAccessible(true);
                        field.set(o1, param);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            fillFields(o, o1);
            return o1;
        }

        private void fillFields(Object old, Object newBean) {
            Field[] newFields = newBean.getClass().getDeclaredFields();
            Class<?> aClass = old.getClass();
            for (Field field : newFields) {
                try {
                    Field f = aClass.getDeclaredField(field.getName());
                    if (!f.canAccess(old)) {
                        f.setAccessible(true);
                    }
                    Object o = f.get(old);
                    if (o != null) {
                        if (!field.canAccess(newFields)) {
                            field.setAccessible(true);
                        }
                        field.set(newFields, o);
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {

                }
            }
        }


        class BaseCallbackHelper<T> extends CallbackHelper {
            private Map<String, Class<?>[]> methodsCache = null;

            private BaseInterceptorProxy interceptorProxy;

            public BaseCallbackHelper(Class superclass, Class[] interfaces) {
                super(superclass, interfaces);
            }

            @SneakyThrows
            @Override
            protected Object getCallback(Method method) {
                if (isProxy(method)) {
                    if (interceptorProxy == null) {
                        interceptorProxy = new BaseInterceptorProxy(Object.class);
                    }
                    return interceptorProxy;
                } else {
                    return NoOp.INSTANCE;
                }
            }

            public void initMethod(Class<?> aClass) {
                if (methodsCache == null) {
                    Method[] methods = aClass.getDeclaredMethods();
                    methodsCache = new HashMap<>();
                    for (Method m : methods) {
                        methodsCache.put(m.getName(), m.getParameterTypes());
                    }
                }
            }

            private boolean isProxy(Method method) {
                if (methodsCache == null) {
                    initMethod(interceptor);
                }
                Class<?>[] curs = null;
                if ((curs = methodsCache.get(method.getName())) == null) {
                    return false;
                }
                Class<?>[] cache = methodsCache.get(method.getName());
                if (cache.length != curs.length) {
                    return false;
                }
                for (int i = 0; i < cache.length; i++) {
                    if (!cache[i].equals(curs[i])) {
                        return false;
                    }
                }

                Method declaredMethod = ClassUtils.getAnnotationMethod(interceptor, method.getName(), method.getParameterTypes(), BaseProxy.class);
                return declaredMethod != null && declaredMethod.getAnnotation(BaseProxy.class) != null;
            }
        }
    }

}
