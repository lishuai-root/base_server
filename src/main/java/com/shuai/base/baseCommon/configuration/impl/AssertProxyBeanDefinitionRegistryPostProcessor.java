package com.shuai.base.baseCommon.configuration.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/20 23:44
 * @version: 1.0
 */

@Component
public class AssertProxyBeanDefinitionRegistryPostProcessor implements BeanFactoryPostProcessor, InstantiationAwareBeanPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }


    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
//        System.out.println("beanName : " + beanName);
//        if (BaseInterceptor.class.isAssignableFrom(beanClass)) {
//            return ParseAssertProxy.createProxy(beanClass);
//        }
        return InstantiationAwareBeanPostProcessor.super.postProcessBeforeInstantiation(beanClass, beanName);
    }
}
