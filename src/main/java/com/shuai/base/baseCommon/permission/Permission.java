package com.shuai.base.baseCommon.permission;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/18 13:10
 * @version: 1.0
 */

public interface Permission extends BasePermission {

    /**
     * 验证用户是否有当前请求接口执行权限
     *
     * @param request
     * @param response
     * @param handler
     * @return true:可执行, false:不可执行
     * @throws Exception 异常
     */
    boolean inspectPermission(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
