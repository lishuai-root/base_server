package com.shuai.base.baseCommon.annotation.basePermission;

import com.shuai.base.baseCommon.common.UserPermission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/19 19:53
 * @version: 1.0
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseIgnoreInterception {

    /**
     * 忽略的拦截器
     *
     * @return
     */
    Class<?>[] baseIgnoreClass() default {Object.class};

    /**
     * 忽略需要的用户权限
     *
     * @return
     */
    int ignorePermission() default UserPermission.USER_PERMISSION;

    /**
     * 是忽略指定拦截器子类拦截器
     *
     * @return
     */
    boolean relax() default true;

    /**
     * 是否忽略前置拦截器
     *
     * @return
     */
    boolean ignorePreHandle() default true;

    /**
     * 是否忽略后置拦截器
     *
     * @return
     */
    boolean ignorePostHandle() default false;

    /**
     * 忽略后置处理
     *
     * @return
     */
    boolean ignoreAfterCompletion() default false;
}
