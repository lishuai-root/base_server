package com.shuai.base.baseCommon.annotation.basePermission;

import com.shuai.base.baseCommon.common.UserPermission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/18 13:54
 * @version: 1.0
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseRequestPermission {

    /**
     * 执行被注解的类或方法的用户权限
     *
     * @return
     */
    int baseExecutePermission() default UserPermission.USER_PERMISSION;
}
