package com.shuai.base.baseCommon.permission;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/19 14:18
 * @version: 1.0
 */

public interface StaticResourceStrategy extends BasePermission {

    /**
     * 处理静态资源
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    boolean staticResourceStrategy(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
