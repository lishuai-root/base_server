package com.shuai.base.baseCommon.baseProxy.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/8/7 23:22
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseMethod {

    @EqualsAndHashCode.Exclude
    Method method;

    String methodName;

    Class<?>[] parameters;
}
