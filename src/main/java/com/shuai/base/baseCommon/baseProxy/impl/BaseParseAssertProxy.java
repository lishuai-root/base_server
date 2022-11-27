package com.shuai.base.baseCommon.baseProxy.impl;

import com.shuai.base.baseCommon.annotation.basePermission.BaseProxy;
import com.shuai.base.baseCommon.baseProxy.AbstractBaseAssertProxy;
import com.shuai.base.utils.ClassUtils;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.proxy.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/8/7 23:03
 * @version: 1.0
 */

public class BaseParseAssertProxy extends AbstractBaseAssertProxy {
    @Override
    public Object createProxy(Class<?> clazz) {
        return new BaseParseAssertProxyEntry(clazz).createProxy();
    }

    @Data
    private static class BaseParseAssertProxyEntry implements CallbackFilter {
        private static final Log logger = LogFactory.getLog(BaseParseAssertProxyEntry.class);
        private final Callback[] callbacks;
        private final Callback baseInterceptorProxy;
        private final Class<?> proxyClass;
        private final Class<?> aClass;
        private Map<Method, Annotation> methodCallback = null;

        public BaseParseAssertProxyEntry(Class<?> clazz) {
            this.aClass = clazz;
            this.baseInterceptorProxy = new BaseInterceptorProxy(clazz);
            this.callbacks = new Callback[]{NoOp.INSTANCE, this.baseInterceptorProxy};
            this.proxyClass = createProxyClass(clazz);
//            Set<BaseMethod> baseMethods = initMethod(proxyClass);
//            methodCallback = new HashSet<>(baseMethods.size());
//
//            for (BaseMethod method : baseMethods) {
//                methodCallback.add(method.getMethod());
//            }
        }


        public Object createProxy() {
            Factory o = (Factory) BeanUtils.instantiateClass(this.proxyClass);
            o.setCallbacks(this.callbacks);
            return o;
        }

        private Class<?> createProxyClass(Class<?> clazz) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(clazz);
            enhancer.setCallbackFilter(this);
            enhancer.setCallbackTypes(new Class[]{NoOp.class, MethodInterceptor.class});
            return enhancer.createClass();
        }


        @Override
        public int accept(Method method) {
            if (methodCallback == null) {
                methodCallback = ClassUtils.getDeclaredMethodsByAnnotation(this.aClass, BaseProxy.class);
            }
            if (methodCallback.containsKey(method)) {
                return 1;
            }
            return 0;
        }
    }
}
