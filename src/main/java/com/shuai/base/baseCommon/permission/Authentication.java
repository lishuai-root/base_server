package com.shuai.base.baseCommon.permission;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/18 13:10
 * @version: 1.0
 */

public interface Authentication extends BasePermission {

    /**
     * 鉴别用户是否登录
     *
     * @param request
     * @param response
     * @param handler
     * @return true:已登录, false:未登录
     * @throws Exception 异常
     */
    boolean inspectAuthentication(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
