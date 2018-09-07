package com.mmlogs.runshell.util;

import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Class RSAUtilTest
 *
 * @author Gene.yang
 * @date 2018/07/17
 */
public class RSAUtilTest {

    /**
     * 货拉拉公共签名
     * @param data  待签名json
     * @return
     */
    public static String sort(JSONObject data) {

        try {
            Object[] keys = data.keySet().toArray(new Object[0]);
            Arrays.sort(keys);
            StringBuilder signStr = new StringBuilder();
            for (Object key : keys) {
                String value = data.get(key.toString()).toString();
                if (value != null) {
                    signStr.append(key).append(value);
                }
            }

            return signStr.toString();
        } catch (Exception e) {
            return "";
        }
    }


    public static void main(String[] args) {
        HashMap<String, String> params = new HashMap<String, String>(5);
        params.put("user_name", "gene.yang");
        params.put("user_pwd_md5", DigestUtils.md5Hex("Hllgene321"));
        params.put("ggcode", "121212");
        params.put("app_key", "51932185d2522e90f938bdec");


        params.put("_sign", EncryptUtil.sslSign(JSONObject.fromObject(params)));
        String url = "http://sso-dev.huolala.cn/index.php?_m=user&_a=login_verify";
        String res = HttpTools.doPost(url, params, 3000, 4000);
        System.out.println("结果：" + res);
    }

    public static void main11(String[] args) throws Exception {
        BufferedReader privateKey1 = new BufferedReader(new FileReader(RSAUtilTest.class.getClassLoader().getResource("pkcs8_private_key.pem").getPath()));
        BufferedReader publicKey1 = new BufferedReader(new FileReader(RSAUtilTest.class.getClassLoader().getResource("rsa_public_key.pem").getPath()));

        String strPrivateKey = "";
        String strPublicKey = "";
        String line = "";
        while((line = privateKey1.readLine()) != null){
            strPrivateKey += line;
        }
        while((line = publicKey1.readLine()) != null){
            strPublicKey += line;
        }
        privateKey1.close();
        publicKey1.close();

        // 私钥需要使用pkcs8格式的，公钥使用x509格式的
        String strPrivKey = strPrivateKey.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "");
        String strPubKey = strPublicKey.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "");

//        System.out.println("pi: " + strPrivKey);
//        System.out.println("pu: " + strPubKey);

        HashMap<String, String> params = new HashMap<String, String>(5);
        params.put("user_name", "gene.yang");
        params.put("user_pwd_md5", DigestUtils.md5Hex("Hllgene321"));
        params.put("ggcode", "121212");
        params.put("app_key", "51932185d2522e90f938bdec");

        String sortVal = sort(JSONObject.fromObject(params));
        byte[] encodedData = RSAUtil.rsaSign(sortVal, strPrivKey);

        System.out.println("hahaha: " + new String(encodedData));
        String __sign = Base64.encodeBase64String(encodedData);
        System.out.println("_sign: " + __sign);

        params.put("_sign", __sign);
        String url = "http://sso-dev.huolala.cn/index.php?_m=user&_a=login_verify";
        String res = HttpTools.doPost(url, params, 3000, 4000);
        System.out.println("结果：" + res);




//        String source = "121212121212";
//        System.out.println("原文字：" + source);
//        byte[] data = source.getBytes();
//        byte[] encodedData = RSAUtil.encryptByPrivateKey(data, strPrivKey);
////        System.out.println("加密后：" + new String(encodedData));
//        byte[] decodedData = RSAUtil.decryptByPublicKey(encodedData, strPubKey);
//        String target = new String(decodedData);
//        System.out.println("解密后: " + target);
//        System.err.println("私钥签名——公钥验证签名");
//        String sign = RSAUtil.sign(encodedData, strPrivKey);
//
//
//        System.err.println("签名: " + sign);
//        boolean status = RSAUtil.verify(encodedData, strPubKey, sign);
//        System.err.println("验证结果: " + status);
    }

    //将byte数组变成RSAPublicKey
//    private static RSAPrivateKey bytes2PK(String privateKey) throws Exception {
//        //通过PKCS#8编码的Key指令获得私钥对象
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
//        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
//        return key;
//    }
}