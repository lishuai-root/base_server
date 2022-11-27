package com.shuai.base.service.userService;

import com.shuai.base.baseCommon.common.User;
import com.shuai.base.baseCommon.message.Message;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/19 19:12
 * @version: 1.0
 */

public interface UserService {

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    Message registerUser(User user);

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    Message updateUserInfo(User user);

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    Message login(User user);

    /**
     * 注销用户
     *
     * @param user
     * @return
     */
    Message cancellationUserInfo(User user);

    boolean createTable();
}
