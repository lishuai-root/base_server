package com.shuai.base.baseCommon.common;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/18 13:52
 * @version: 1.0
 */

public class UserPermission {

    /**
     * root用户权限
     */
    public static final int ROOT_PERMISSION = 0;

    /**
     * 管理员用户权限
     */
    public static final int ADMIN_PERMISSION = 2;

    /**
     * 普通用户权限
     */
    public static final int USER_PERMISSION = 4;

    /**
     * 禁言，可登录
     */
    public static final int USER_ESTOPPEL = 6;

    /**
     * 限时登录
     */
    public static final int USER_LIMITED_TIME_LOGIN = 8;

    /**
     * 限制登录
     */
    public static final int USER_RESTRICT_LOGIN = 10;

    /**
     * 默认请求执行权限
     */
    public static final int DEFAULT_PERMISSION = Integer.MAX_VALUE;

    /**
     * 已登录
     */
    public static final int USER_LOGGED = 0;


    /**
     * 未登录
     */
    public static final int STATUS_NOT_LOGIN = 2;


    /**
     * 已退出
     */
    public static final int USER_EXISTED = 4;

    /**
     * 执行失败
     */
    public static final boolean SUCCESSFUL_FALSE = false;

    /**
     * 执行成功
     */
    public static final boolean SUCCESSFUL_TRUE = true;


    /**
     * 默认20分钟超时(毫秒)
     */
    public static final long DEFAULT_TIMEOUT_MS = 1000 * 60 * 20;

    /**
     * 默认20分钟超时(秒)
     */
    public static final int DEFAULT_TIMEOUT_S = (int) (DEFAULT_TIMEOUT_MS / 1000);

    /**
     * 同意请求最小间隔时间
     */
    public static final long NETWORK_SECURITY_TIME = 1000L;

    /**
     * 默认用户名称
     */
    public static final String DEFAULT_USER_NAME = "User_";
}
