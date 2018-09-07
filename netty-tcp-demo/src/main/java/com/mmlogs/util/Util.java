package com.mmlogs.util;


import com.alibaba.fastjson.JSONObject;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Class Util
 * 常用操作工具
 *
 * @author Gene.yang
 * @date 2018/06/24
 */
public class Util {
    private static AtomicInteger autoIncrement = new AtomicInteger();

    public static int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str) || "null".equals(str)) {
            return true;
        }

        return false;
    }

    /**
     * 判断json是否为空
     * @param data
     * @return
     */
    public static boolean isEmpty(JSONObject data) {
        if (data == null || "".equals(data.toString()) || data.isEmpty()) {
            return true;
        }

        return false;
    }






    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }




    public static boolean isNotEmpty(JSONObject json) {
        return !isEmpty(json);
    }

    public static String getNowTime() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(now);
    }

    public static String getFormatTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }



    public static String signMD5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes("UTF-8"));
            byte[] digest = md5.digest();
            String hex = toHex(digest);
            return hex;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String toHex(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        int len = digest.length;
        String out = null;
        for (int i = 0; i < len; i++) {
            out = Integer.toHexString(0xFF & digest[i]);
            if (out.length() == 1) {
                sb.append("0");
            }
            sb.append(out);
        }
        return sb.toString();
    }


    public static String tokenSign(String secret, JSONObject jsonData) {
        Object[] keys = jsonData.keySet().toArray(new Object[0]);
        Arrays.sort(keys);
        StringBuffer query = new StringBuffer();
        query.append(secret);
        for (Object key : keys) {
            String value = jsonData.get(key.toString()).toString();
            if (Util.isNotEmpty(key.toString()) && Util.isNotEmpty(value)) {
                query.append(key).append(value);
            }
        }
        query.append(secret);
        return signMD5(query.toString()).toUpperCase();
    }


    public static long generateSeqId() {
        // 获取自增ID
        int autoSeq = autoIncrement.getAndIncrement();
        if (autoSeq >= 10000) {
            synchronized (autoIncrement) {
                if (autoIncrement.get() >= 10000) {
                    autoIncrement.set(0);
                }
            }
            autoSeq = autoIncrement.getAndIncrement();
        }

        long time = System.currentTimeMillis();
        String autoSeqStr = String.valueOf(autoSeq);
        if (autoSeqStr.length() > 4) {
            autoSeqStr = autoSeqStr.substring(0, 4);
        }
        int count = 4 - autoSeqStr.length();
        for (int i = 0; i < count; i++) {
            autoSeqStr = "0" + autoSeqStr;
        }
        return Long.parseLong(time + autoSeqStr);
    }
}
