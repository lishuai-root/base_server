package com.shuai.base.baseCommon.common;

import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/18 14:10
 * @version: 1.0
 */

@Service
public class UserMessage {

    public User createUser(String userName, String passWord) {
        User user = new User(userName);
        user.setPassWord(passWord);
        user.setLoginTime(System.currentTimeMillis());
        user.setPermission(UserPermission.USER_PERMISSION);
        return user;
    }
}
