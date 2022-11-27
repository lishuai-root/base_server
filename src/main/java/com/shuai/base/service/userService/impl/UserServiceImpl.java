package com.shuai.base.service.userService.impl;

import com.shuai.base.baseCommon.common.User;
import com.shuai.base.baseCommon.common.UserPermission;
import com.shuai.base.baseCommon.common.UserUtils;
import com.shuai.base.baseCommon.message.Message;
import com.shuai.base.dao.userDao.BaseUserBaseDao;
import com.shuai.base.service.userService.PermissionCheckService;
import com.shuai.base.service.userService.UserService;
import com.shuai.base.utils.MessageUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/19 19:12
 * @version: 1.0
 */

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    BaseUserBaseDao baseUserBaseDao;

    @Autowired
    PermissionCheckService permissionCheckService;

    @Override
    public Message registerUser(User user) {
        User dbUser = baseUserBaseDao.selectByLoginName(user.getLoginName());
        if (dbUser != null) {
            return MessageUtils.loginError("用户名不可用。");
        }
        String userId = UserUtils.getCooKieByID(UUID.randomUUID().toString());
        user.setUserId(userId);
        user.setPermission(UserPermission.USER_PERMISSION);
        user.setPassWord(UserUtils.getEncryptedPassword(user.getPassWord()));
        UserUtils.setDefaultUserName(user);
        int i = baseUserBaseDao.insertSelective(user);
        if (i > 0) {
            return MessageUtils.ok(user);
        } else {
            return MessageUtils.error("register error.");
        }
    }

    @Override
    public Message updateUserInfo(User user) {
        return null;
    }

    @Override
    public Message login(User user) {
        if (!StringUtils.hasText(user.getLoginName()) || !StringUtils.hasText(user.getPassWord())) {
            return MessageUtils.loginError("登录账号或密码错误。");
        }

        User dbUser = baseUserBaseDao.selectByLoginName(user.getLoginName());
        if (dbUser == null) {
            return MessageUtils.loginError("用户未注册。");
        }

        if (!UserUtils.checkUserPassword(UserUtils.getEncryptedPassword(user.getPassWord()), dbUser.getPassWord())) {
            return MessageUtils.loginError("登录账号或密码错误。");
        }
        return permissionCheckService.checkUserPermission(dbUser);
    }

    @Override
    public Message cancellationUserInfo(User user) {
        return null;
    }

    @Override
    public boolean createTable() {
        return baseUserBaseDao.createTable() >= 1;
    }
}
