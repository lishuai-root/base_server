package com.shuai.base.service.userService.impl;

import com.shuai.base.baseCommon.common.User;
import com.shuai.base.baseCommon.common.UserPermission;
import com.shuai.base.baseCommon.common.UserUtils;
import com.shuai.base.baseCommon.message.Message;
import com.shuai.base.dao.userDao.BaseUserBaseDao;
import com.shuai.base.service.userService.PermissionCheckService;
import com.shuai.base.utils.MessageUtils;
import com.shuai.base.utils.TimeUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/20 12:15
 * @version: 1.0
 */

@Log4j2
@Service("permissionCheckService")
public class PermissionCheckServiceImpl implements PermissionCheckService {

    BaseUserBaseDao baseUserBaseDao;

    @Override
    public Message checkUserPermission(User user) {
        if (!UserUtils.isCanLoginIn(user.getPermission())) {
            if (UserUtils.isPermanentBan(user.getPermission())) {
                return MessageUtils.loginError("当前用户不可登录。");
            }
            if (UserUtils.isExceedLimitedLogin(user.getRestrictLoginStart(), user.getLimitLoginDuration())) {
                return MessageUtils.loginError("当前用户限制登录，请于" +
                        TimeUtils.getDate(user.getRestrictLoginStart() + user.getLimitLoginDuration()) + "后登录。");
            }
            user.setPermission(UserPermission.USER_PERMISSION);
            if (baseUserBaseDao.releaseRestrictionUser(user) != 1) {
                return MessageUtils.loginError("登录失败。");
            }
        }
        log.info(user.getLoginName() + "登录成功。");
        return MessageUtils.loginOk(user);
    }
}
