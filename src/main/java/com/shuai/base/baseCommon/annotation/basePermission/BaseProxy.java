package com.shuai.base.baseCommon.annotation.basePermission;

import java.lang.annotation.*;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/21 10:00
 * @version: 1.0
 */

@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseProxy {

    /**
     * 方法名
     *
     * @return
     */
    String method() default "";

    /**
     * 参数列表
     *
     * @return
     */
    Class<?>[] parameter() default {};

    /**
     * 每个方法预期的结果
     *
     * @return
     */
    boolean expect() default true;

    /**
     * 是否代理私有方法
     *
     * @return
     */
    boolean accessible() default true;

    /**
     * @return
     */
    boolean defaultValue() default true;

    /**
     * 不执行时，返回预期值
     *
     * @return
     */
    boolean returnExp() default true;
}
