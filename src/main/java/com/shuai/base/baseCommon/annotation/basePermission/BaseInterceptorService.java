package com.shuai.base.baseCommon.annotation.basePermission;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/8/6 21:39
 * @version: 1.0
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface BaseInterceptorService {

    String[] addPathPatterns() default {"/**"};

    String[] excludePathPatterns() default {};

    int order() default Integer.MAX_VALUE;
}
