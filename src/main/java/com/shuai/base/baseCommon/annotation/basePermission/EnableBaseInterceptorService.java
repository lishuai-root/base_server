package com.shuai.base.baseCommon.annotation.basePermission;

import com.shuai.base.baseCommon.configuration.impl.InterceptorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/8/8 20:08
 * @version: 1.0
 */

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(InterceptorConfiguration.class)
public @interface EnableBaseInterceptorService {
}
