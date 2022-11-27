package com.shuai.base.baseCommon.baseProxy;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/8/7 21:28
 * @version: 1.0
 */

public interface BaseAssertProxy {

    /**
     * 创建一个代理对象
     *
     * @param clazz
     * @return
     */
    Object createProxy(Class<?> clazz);
}
