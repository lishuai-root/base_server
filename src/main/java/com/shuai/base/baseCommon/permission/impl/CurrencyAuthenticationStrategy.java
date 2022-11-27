package com.shuai.base.baseCommon.permission.impl;

import com.shuai.base.baseCommon.permission.DefaultAuthenticationStrategy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/18 18:58
 * @version: 1.0
 */

@Service
public class CurrencyAuthenticationStrategy extends DefaultAuthenticationStrategy {

    @Override
    public boolean inspectAuthentication(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.inspectAuthentication(request, response, handler);
    }

    @Override
    public boolean inspectPermission(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.inspectPermission(request, response, handler);
    }

    @Override
    public boolean inspectNetworkSecurity(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.inspectNetworkSecurity(request, response, handler);
    }

    @Override
    public boolean staticResourceStrategy(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.staticResourceStrategy(request, response, handler);
    }
}
