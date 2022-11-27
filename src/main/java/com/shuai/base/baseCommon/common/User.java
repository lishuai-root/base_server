package com.shuai.base.baseCommon.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/18 12:00
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public final class User {
    /**
     * 用户登录名
     */
    private String loginName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    @JsonIgnore
    transient private String passWord;

    /***
     * 用户角色id
     */
    private String userRoleId;

    /**
     * 用户角色名称
     */
    private String userRoleName;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户电话
     */
    private String iphone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户签名
     */
    private String signature;

    /**
     * 当前用户会话id
     */
    @JsonIgnore
    transient private String requestId;

    /**
     * 用户权限
     */
    @JsonIgnore
    transient private int permission;

    /**
     * 登录时间
     */
    private long loginTime;

    /**
     * 最后一次访问时间
     */
    @JsonIgnore
    transient private long lastActivityTime;

    /**
     * 会话保持时长
     */
    @JsonIgnore
    transient private long effectiveTime;

    /**
     * 限制登陆开始时间
     */
    @JsonIgnore
    transient private long restrictLoginStart;

    /**
     * 限制登陆时长
     */
    @JsonIgnore
    transient private long limitLoginDuration;


    /**
     * 用户当前登录状态
     */
    @JsonIgnore
    transient private int loginStatus;


    public User(String loginName) {
        this.loginName = loginName;
        this.userName = "测试";
        this.userRoleName = "用户";
    }
}
