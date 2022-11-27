package com.shuai.base.utils;

import java.text.SimpleDateFormat;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/20 12:33
 * @version: 1.0
 */

public class TimeUtils {
    public static String FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getDate(long timestamp) {
        return new SimpleDateFormat(FORMAT).format(timestamp);
    }

    public static void main(String[] args) {
        System.out.println(getDate(System.currentTimeMillis()));
    }
}
