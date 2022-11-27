package com.shuai.base.baseCommon.configuration;

import com.shuai.base.baseCommon.baseProxy.BaseAssertProxy;
import com.shuai.base.baseCommon.baseProxy.impl.BaseParseAssertProxy;
import com.shuai.base.baseCommon.interceptor.BaseInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.CglibSubclassingInstantiationStrategy;
import org.springframework.beans.factory.support.RootBeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/8/7 20:42
 * @version: 1.0
 */

public class BaseInstantiationStrategy extends CglibSubclassingInstantiationStrategy {

    private static final Map<Class<?>, BaseAssertProxy> proxyInstantiationStrategy;

    static {
        proxyInstantiationStrategy = new HashMap<>();
        proxyInstantiationStrategy.put(BaseInterceptor.class, new BaseParseAssertProxy());
    }

    protected BaseAssertProxy getBaseAssertProxy(Class<?> beanClass) {
        LinkedList<Class<?>> queue = new LinkedList<>();
        queue.addLast(beanClass);
        while (!queue.isEmpty()) {
            beanClass = queue.removeFirst();
            if (beanClass != Object.class) {
                if (proxyInstantiationStrategy.containsKey(beanClass)) {
                    return proxyInstantiationStrategy.get(beanClass);
                }
                Class<?> superclass = beanClass.getSuperclass();
                if (superclass != null) {
                    queue.addLast(superclass);
                }
                queue.addAll(Arrays.asList(beanClass.getInterfaces()));
            }
        }
        return null;
    }

    protected boolean shouldProxy(RootBeanDefinition beanDefinition) {
        return beanDefinition.hasBeanClass() && BaseInterceptor.class.isAssignableFrom(beanDefinition.getBeanClass());
    }

    protected Object createProxyInstantiate(RootBeanDefinition rootBeanDefinition) {
        Object proxyInstance = null;
        if (shouldProxy(rootBeanDefinition)) {
            final Class<?> beanClass = rootBeanDefinition.getBeanClass();
            BaseAssertProxy baseAssertProxy = getBaseAssertProxy(beanClass);
            if (baseAssertProxy != null) {
                proxyInstance = baseAssertProxy.createProxy(beanClass);
            }
        }
        return proxyInstance;
    }

    @Override
    public Object instantiate(RootBeanDefinition rootBeanDefinition, String s, BeanFactory beanFactory) throws BeansException {
        Object bean = createProxyInstantiate(rootBeanDefinition);
        if (bean == null) {
            bean = super.instantiate(rootBeanDefinition, s, beanFactory);
        }
        return bean;
    }

    @Override
    public Object instantiate(RootBeanDefinition rootBeanDefinition, String s, BeanFactory beanFactory, Constructor<?> constructor, Object... objects) throws BeansException {
        Object bean = createProxyInstantiate(rootBeanDefinition);
        if (bean == null) {
            bean = super.instantiate(rootBeanDefinition, s, beanFactory, constructor, objects);
        }
        return bean;
    }

    @Override
    public Object instantiate(RootBeanDefinition rootBeanDefinition, String s, BeanFactory beanFactory, Object o, Method method, Object... objects) throws BeansException {
        Object bean = createProxyInstantiate(rootBeanDefinition);
        if (bean == null) {
            bean = super.instantiate(rootBeanDefinition, s, beanFactory, o, method, objects);
        }
        return bean;
    }
}
