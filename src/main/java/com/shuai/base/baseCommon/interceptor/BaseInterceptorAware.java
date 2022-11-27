package com.shuai.base.baseCommon.interceptor;

import lombok.Data;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/8/7 15:45
 * @version: 1.0
 */

@Data
public class BaseInterceptorAware {

    HandlerInterceptor baseInterceptor;

    Map<String, Object> attributes;

    public BaseInterceptorAware(HandlerInterceptor baseInterceptor, Map<String, Object> attributes) {
        this.baseInterceptor = baseInterceptor;
        this.attributes = attributes;
    }
}
