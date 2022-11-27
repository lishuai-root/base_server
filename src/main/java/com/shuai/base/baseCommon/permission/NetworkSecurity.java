package com.shuai.base.baseCommon.permission;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/19 13:52
 * @version: 1.0
 */

public interface NetworkSecurity extends BasePermission {

    /**
     * 网络拦截器，鉴别恶意请求
     *
     * @param request
     * @param response
     * @param handler
     * @return true:可执行, false:不可执行
     * @throws Exception 异常
     */
    boolean inspectNetworkSecurity(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
