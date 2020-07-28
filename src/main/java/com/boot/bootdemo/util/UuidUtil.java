package com.boot.bootdemo.util;

import java.util.UUID;

/**
 * @author Administrator
 * @date 2020/7/27 17:11
 */
public class UuidUtil {
    private static long currentOrder = System.currentTimeMillis();
    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String get32UUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static synchronized long getDefaultOrder() {
        if (System.currentTimeMillis() <= currentOrder)
            return ++currentOrder;
        else
            return currentOrder = System.currentTimeMillis();
    }
    public static void main(String[] args) {
        System.out.println(get32UUID());
    }
}
