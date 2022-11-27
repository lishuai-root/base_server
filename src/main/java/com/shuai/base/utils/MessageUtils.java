package com.shuai.base.utils;

import com.shuai.base.baseCommon.common.ExecuteStatus;
import com.shuai.base.baseCommon.common.User;
import com.shuai.base.baseCommon.common.UserPermission;
import com.shuai.base.baseCommon.message.ResponseMessage;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/18 20:46
 * @version: 1.0
 */

@Log4j2
public class MessageUtils {

    public static ResponseMessage noAuthority() {
        ResponseMessage responseMessage = new ResponseMessage(ExecuteStatus.STATUS_NO_AUTHORITY, UserPermission.SUCCESSFUL_FALSE);
        Map<String, String> map = new HashMap<>();
        map.put("result", "no permission execute.");
        responseMessage.setData(map);
        return responseMessage;
    }

    public static ResponseMessage timeout() {
        ResponseMessage responseMessage = new ResponseMessage(ExecuteStatus.STATUS_TIMEOUT, UserPermission.SUCCESSFUL_FALSE);
        Map<String, String> map = new HashMap<>();
        map.put("result", "timeout, please reLogin.");
        responseMessage.setData(map);
        return responseMessage;
    }

    public static ResponseMessage ok() {
        ResponseMessage responseMessage = new ResponseMessage(ExecuteStatus.STATUS_OK, UserPermission.SUCCESSFUL_TRUE);
        Map<String, String> map = new HashMap<>();
        map.put("result", "execute ok.");
        responseMessage.setData(map);
        return responseMessage;
    }

    public static ResponseMessage ok(Object o) {
        ResponseMessage responseMessage = ok();
        responseMessage.setData(o);
        return responseMessage;
    }

    public static ResponseMessage error() {
        ResponseMessage responseMessage = new ResponseMessage(ExecuteStatus.STATUS_ERROR, UserPermission.SUCCESSFUL_FALSE);
        Map<String, String> map = new HashMap<>();
        map.put("result", "execute error.");
        responseMessage.setData(map);
        return responseMessage;
    }

    public static ResponseMessage error(String msg) {
        ResponseMessage responseMessage = error();
        responseMessage.setData(msg);
        return responseMessage;
    }

    public static ResponseMessage restrictLogin(User user) {
        ResponseMessage responseMessage = error();
        if (user.getPermission() == UserPermission.USER_LIMITED_TIME_LOGIN) {
            responseMessage.setData("当前用户限制登陆");
        }
        return null;
    }

    public static ResponseMessage notLogin() {
        ResponseMessage responseMessage = new ResponseMessage(UserPermission.STATUS_NOT_LOGIN, UserPermission.SUCCESSFUL_FALSE);
        Map<String, String> map = new HashMap<>();
        map.put("result", "execute error.");
        responseMessage.setData(map);
        return responseMessage;
    }

    public static ResponseMessage exit() {
        ResponseMessage responseMessage = new ResponseMessage(ExecuteStatus.STATUS_EXITED, UserPermission.SUCCESSFUL_TRUE);
        Map<String, String> map = new HashMap<>();
        map.put("result", "exited.");
        responseMessage.setData(map);
        return responseMessage;
    }


    public static ResponseMessage login() {
        ResponseMessage responseMessage = new ResponseMessage(ExecuteStatus.STATUS_LOGIN, UserPermission.SUCCESSFUL_TRUE);
        Map<String, String> map = new HashMap<>();
        map.put("result", "logged in, please do not log in again.");
        responseMessage.setData(map);
        return responseMessage;
    }

    public static ResponseMessage loginOk(User user) {
        ResponseMessage responseMessage = new ResponseMessage(ExecuteStatus.STATUS_LOGIN_OK, UserPermission.SUCCESSFUL_TRUE);
        responseMessage.setData(user);
        return responseMessage;
    }

    public static ResponseMessage loginError() {
        ResponseMessage responseMessage = new ResponseMessage(ExecuteStatus.STATUS_LOGIN_ERROR, UserPermission.SUCCESSFUL_FALSE);
        Map<String, String> map = new HashMap<>();
        map.put("result", "login error.");
        responseMessage.setData(map);
        return responseMessage;
    }

    public static ResponseMessage loginError(String msg) {
        ResponseMessage responseMessage = loginError();
        responseMessage.setData(msg);
        return responseMessage;
    }
}
