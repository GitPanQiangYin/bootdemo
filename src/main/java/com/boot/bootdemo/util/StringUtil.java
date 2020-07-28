package com.boot.bootdemo.util;

/**
 * @author Administrator
 * @date 2020/7/27 15:03
 */
public class StringUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || "undefined".equals(str) || "null".equals(str) || "".equals(str);
    }
}
