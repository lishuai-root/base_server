package com.shuai.base.utils;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/18 14:07
 * @version: 1.0
 */

public class StringUtils {

    public static boolean isEmpty(Object[] strs) {
        return strs == null || strs.length == 0;
    }

    public static void main(String[] args) {
        System.out.println(String.class.isAssignableFrom(null));
    }
}
