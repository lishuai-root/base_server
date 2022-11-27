package com.shuai.base.baseCommon.common;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/18 14:24
 * @version: 1.0
 */

public class UserUtils {

    private static final int COOKIE_SIZE = 16;
    private static final char ID_SEPARATOR = '-';

    public static String getUserRequestId() {
        return UUID.randomUUID().toString();
    }

    public static String getCooKieByRequestID(String requestID) {
        return requestID;
    }

    public static String getCooKieByID(String id) {
        char[] chars = new char[COOKIE_SIZE];
        int index = 0, k = 0;
        for (int i = 0; i < id.length(); i++) {
            if (ID_SEPARATOR == id.charAt(i)) {
                continue;
            }
            if ((k & 1) == 0) {
                chars[index++] = id.charAt(i);
            }
            ++k;
        }
        return String.valueOf(chars, 0, index);
    }

    public static boolean checkCookidByID(String cookie, String id) {
        int index = 0, k = 0;
        for (int i = 0; i < id.length(); i++) {
            if (ID_SEPARATOR == id.charAt(i)) {
                continue;
            }
            if ((k & 1) == 0 && cookie.charAt(index++) != id.charAt(i)) {
                return false;
            }
            ++k;
        }
        return index >= cookie.length();
    }

    public static String getEncryptedPassword(String sourcePassword) {
        return DigestUtils.md5DigestAsHex(sourcePassword.getBytes());
    }

    public static boolean checkOutTime(User user) {
        return user.getLastActivityTime() + user.getEffectiveTime() <= System.currentTimeMillis();
    }

    public static boolean checkUserPassword(String userPassword, String matchPassword) {
        return userPassword.equals(matchPassword);
    }

    public static boolean isCanLoginIn(int userPermission) {
        return userPermission <= UserPermission.USER_ESTOPPEL;
    }

    public static boolean isActive(int userPermission) {
        return userPermission <= UserPermission.USER_PERMISSION;
    }

    public static boolean isPermanentBan(int userPermission) {
        return userPermission == UserPermission.USER_RESTRICT_LOGIN;
    }

    public static boolean isLimitedLogin(int userPermission) {
        return userPermission == UserPermission.USER_LIMITED_TIME_LOGIN;
    }

    public static boolean isExceedLimitedLogin(long startTime, long limitTime) {
        return startTime + limitTime <= System.currentTimeMillis();
    }

    public static boolean hasPermission(int userPermission, int needPermission) {
        return userPermission <= needPermission;
    }

    public static boolean isSecurityTime(long currentTime, long lastTime) {
        return currentTime - lastTime >= UserPermission.NETWORK_SECURITY_TIME;
    }

    public static String getDefaultUserName() {
        return UserPermission.DEFAULT_USER_NAME + System.currentTimeMillis();
    }

    public static void setDefaultUserName(User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            user.setUserName(getDefaultUserName());
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String encryptedPassword = getEncryptedPassword("1233lfhafsdlfh");
        System.out.println(encryptedPassword);
        System.out.println(encryptedPassword.length());
    }
}
