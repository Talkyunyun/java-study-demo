package com.mmlogs.usage.javausage.util;

import java.util.Random;

public class RandomUtil {

    static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 返回指定长度随机字符串
     * @param length
     * @return
     */
    public static String getString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(letterChar.length())));
        }
        return sb.toString();
    }
}
