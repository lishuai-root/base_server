package com.shuai.base.baseCommon.common;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/7/31 23:45
 * @version: 1.0
 */

public class ExecuteStatus {


    /**
     * 执行成功
     */
    public static final int STATUS_OK = 0;

    /**
     * 执行失败
     */
    public static final int STATUS_ERROR = 1;

    /**
     * 没有执行权限
     */
    public static final int STATUS_NO_AUTHORITY = 101;

    /**
     * 登录超时
     */
    public static final int STATUS_TIMEOUT = 102;

    /**
     * 退出登录
     */
    public static final int STATUS_EXITED = 103;

    /**
     * 登录成功
     */
    public static final int STATUS_LOGIN_OK = 104;

    /**
     * 已登录
     */
    public static final int STATUS_LOGIN = 105;

    /**
     * 登录失败
     */
    public static final int STATUS_LOGIN_ERROR = 106;
}
