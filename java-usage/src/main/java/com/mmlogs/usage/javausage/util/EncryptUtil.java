package com.mmlogs.usage.javausage.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;


/**
 * 加密工具类
 *
 * @author Gene
 */
@Service
public class EncryptUtil {

    /**
     * md5加密
     * @param str 待加密字符串
     * @return 返回加密之后的字符串
     */
    public String md5(String str) {

        return DigestUtils.md5Hex(str);
    }


    /**
     * base64编码
     * @param str
     * @return
     */
    public String base64Encode(String str) {

        return Base64.encodeBase64String(str.getBytes());
    }

    /**
     * base64解码
     * @param str
     * @return
     */
    public String base64Decode(String str) {

        return new String(Base64.decodeBase64(str.getBytes()));
    }



    /**
     * 签名算法
     * @param data
     * @param secret
     * @return
     */
    public String sign(JSONObject data, String secret) {
        if (null == data || null == secret) {
            return null;
        }

        try {
            Object[] keys = data.keySet().toArray(new Object[0]);
            Arrays.sort(keys);
            StringBuilder signStr = new StringBuilder(secret);
            for (Object key : keys) {
                String value = data.get(key.toString()).toString();
                if (value != null) {
                    signStr.append(key).append(value);
                }
            }
            signStr.append(secret);

            return DigestUtils.md5Hex(signStr.toString()).toUpperCase();
        } catch (Exception e) {
            return null;
        }
    }
}
