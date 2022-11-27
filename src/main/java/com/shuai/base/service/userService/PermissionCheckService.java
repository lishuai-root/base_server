package com.shuai.base.service.userService;

import com.shuai.base.baseCommon.common.User;
import com.shuai.base.baseCommon.message.Message;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/20 12:14
 * @version: 1.0
 */

public interface PermissionCheckService {

    /**
     * 检查用户权限
     *
     * @param user
     * @return
     */
    Message checkUserPermission(User user);
}
