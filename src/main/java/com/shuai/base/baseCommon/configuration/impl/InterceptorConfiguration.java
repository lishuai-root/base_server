package com.shuai.base.baseCommon.configuration.impl;

import com.shuai.base.baseCommon.annotation.basePermission.BaseInterceptorService;
import com.shuai.base.baseCommon.baseProxy.impl.ParseAssertProxy;
import com.shuai.base.baseCommon.configuration.BaseInstantiationStrategy;
import com.shuai.base.baseCommon.interceptor.BaseInterceptor;
import com.shuai.base.baseCommon.interceptor.BaseInterceptorAware;
import com.shuai.base.utils.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/18 15:00
 * @version: 1.0
 */


public class InterceptorConfiguration implements WebMvcConfigurer, BeanFactoryPostProcessor, InstantiationAwareBeanPostProcessor {

    private static final String INTERCEPTOR_REGISTRY_STATE = "INTERCEPTOR_REGISTRY_STATE";
    //    private final CurrencyAuthenticationStrategy currencyAuthenticationStrategy = new CurrencyAuthenticationStrategy();
    private ConfigurableListableBeanFactory beanFactory;
    private boolean useBaseInstantiationStrategy = false;

//    @Bean(name = "authentication")
//    public Authentication getAuthentication() {
//        return currencyAuthenticationStrategy;
//    }
//
//    @Bean(name = "permission")
//    public Permission getPermission() {
//        return currencyAuthenticationStrategy;
//    }
//
//    @Bean(name = "networkSecurity")
//    public NetworkSecurity getNetworkSecurity() {
//        return currencyAuthenticationStrategy;
//    }
//
//    @Bean(name = "staticResourceStrategy")
//    public StaticResourceStrategy getStaticResourceStrategy() {
//        return currencyAuthenticationStrategy;
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<BaseInterceptorAware> interceptorRegistration = getInterceptorRegistration(this.beanFactory);
        for (BaseInterceptorAware baseInterceptor : interceptorRegistration) {
            if (baseInterceptor.getAttributes() != null) {
                Map<String, Object> attributes = baseInterceptor.getAttributes();
                InterceptorRegistration registration = registry.addInterceptor(baseInterceptor.getBaseInterceptor())
                        .order((Integer) attributes.get("order"));
                Object pathPatterns = attributes.get("addPathPatterns");
                if (pathPatterns instanceof String[] && !StringUtils.isEmpty((String[]) pathPatterns)) {
                    registration.addPathPatterns((String[]) pathPatterns);
                }
                pathPatterns = attributes.get("excludePathPatterns");
                if (pathPatterns instanceof String[] && !StringUtils.isEmpty((String[]) pathPatterns)) {
                    registration.excludePathPatterns((String[]) pathPatterns);
                }
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof AbstractAutowireCapableBeanFactory) {
            AbstractAutowireCapableBeanFactory abf = (AbstractAutowireCapableBeanFactory) beanFactory;
            abf.setInstantiationStrategy(new BaseInstantiationStrategy());
            this.useBaseInstantiationStrategy = true;
        }
        this.beanFactory = beanFactory;
    }

    protected List<BaseInterceptorAware> getInterceptorRegistration(ConfigurableListableBeanFactory beanFactory) {
        String[] interceptorBeanDefinitionNames = beanFactory.getBeanNamesForType(HandlerInterceptor.class);
        List<BaseInterceptorAware> interceptorRegistration = new ArrayList<>(interceptorBeanDefinitionNames.length);

        for (String beanDefinitionName : interceptorBeanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            if (Boolean.TRUE.equals(beanDefinition.getAttribute(INTERCEPTOR_REGISTRY_STATE))) {
                continue;
            }
            if (beanDefinition instanceof AnnotatedBeanDefinition) {
                AnnotationMetadata metadata = ((AnnotatedBeanDefinition) beanDefinition).getMetadata();

                if (metadata.hasAnnotation(BaseInterceptorService.class.getName())) {
                    HandlerInterceptor bean = (HandlerInterceptor) beanFactory.getBean(beanDefinitionName);
                    if (!Boolean.TRUE.equals(useBaseInstantiationStrategy)) {
                        bean = initInterceptor(beanFactory, bean, beanDefinitionName);
                    }
                    Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(BaseInterceptorService.class.getName());

                    if (annotationAttributes.getOrDefault("order", Integer.MAX_VALUE).equals(Integer.MAX_VALUE)) {
                        Map<String, Object> orderAttributes = metadata.getAnnotationAttributes(Order.class.getName());
                        if (orderAttributes != null
                                && !orderAttributes.getOrDefault("value", Integer.MAX_VALUE).equals(Integer.MAX_VALUE)) {
                            annotationAttributes.put("order", orderAttributes.get("value"));
                        }
                    }
                    interceptorRegistration.add(new BaseInterceptorAware(bean, annotationAttributes));
                    beanDefinition.setAttribute(INTERCEPTOR_REGISTRY_STATE, true);
                }
            }
        }
        return interceptorRegistration;
    }


    protected HandlerInterceptor initInterceptor(ConfigurableListableBeanFactory beanFactory, HandlerInterceptor bean, String beanDefinitionName) {
        beanFactory.autowireBean(bean);
        beanFactory.initializeBean(bean, beanDefinitionName);
        return bean;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (!Boolean.TRUE.equals(useBaseInstantiationStrategy) && BaseInterceptor.class.isAssignableFrom(beanClass)) {
            return ParseAssertProxy.createProxy(beanClass);
        }
        return InstantiationAwareBeanPostProcessor.super.postProcessBeforeInstantiation(beanClass, beanName);
    }
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
////        InterceptorRegistration networkInterceptor = registry.addInterceptor(new NetworkSecurityInterceptor(currencyAuthenticationStrategy));
//        BaseInterceptor netInterceptor = (BaseInterceptor) ParseAssertProxy.createProxy(new NetworkSecurityInterceptor(), currencyAuthenticationStrategy);
//        InterceptorRegistration networkInterceptor = registry.addInterceptor(netInterceptor);
//        networkInterceptor.addPathPatterns("/**");
//
////        InterceptorRegistration loginInterceptor = registry.addInterceptor(new LoginInterceptor(currencyAuthenticationStrategy));
//        BaseInterceptor loginInter = (BaseInterceptor) ParseAssertProxy.createProxy(new LoginInterceptor(), currencyAuthenticationStrategy);
//        InterceptorRegistration loginInterceptor = registry.addInterceptor(loginInter);
//        loginInterceptor.addPathPatterns("/**");
//        loginInterceptor.excludePathPatterns("/static/**");
//
////        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new PermissionInterceptor(currencyAuthenticationStrategy));
//        BaseInterceptor permissionInterceptor = (BaseInterceptor) ParseAssertProxy.createProxy(new PermissionInterceptor(), currencyAuthenticationStrategy);
//        InterceptorRegistration interceptorRegistration = registry.addInterceptor(permissionInterceptor);
//        interceptorRegistration.addPathPatterns("/**");
//        interceptorRegistration.excludePathPatterns("/static/**", "/base/login/**");
//
////        InterceptorRegistration staticResource = registry.addInterceptor(new StaticInterceptor(currencyAuthenticationStrategy));
//        BaseInterceptor staticResourceInterceptor = (BaseInterceptor) ParseAssertProxy.createProxy(new StaticInterceptor(), currencyAuthenticationStrategy);
//        InterceptorRegistration staticResource = registry.addInterceptor(staticResourceInterceptor);
//        staticResource.addPathPatterns("/static/**");
//    }
}
