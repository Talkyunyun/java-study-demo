package com.mmlogs.runshell.util;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;


public class EncryptUtilTest {

    @Test
    public void opensslEncode() throws Exception {

        JSONObject data = new JSONObject();
        data.put("username", "Gene");
        data.put("password", 13);
        data.put("app_key", 13);
        data.put("_sign", 13);


        String s = data.toString();
        try {

            BufferedReader privateKey = new BufferedReader(new FileReader(getClass().getClassLoader().getResource("pkcs8_private_key.pem").getPath()));
            BufferedReader publicKey = new BufferedReader(new FileReader(getClass().getClassLoader().getResource("rsa_public_key.pem").getPath()));
            String strPrivateKey = "";
            String strPublicKey = "";
            String line = "";
            while((line = privateKey.readLine()) != null){
                strPrivateKey += line;
            }
            while((line = publicKey.readLine()) != null){
                strPublicKey += line;
            }
            privateKey.close();
            publicKey.close();

            // 私钥需要使用pkcs8格式的，公钥使用x509格式的
            String strPrivKey = strPrivateKey.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "");
            String strPubKey = strPublicKey.replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "");

            byte [] privKeyByte = Base64.decodeBase64(strPrivKey);
            byte [] pubKeyByte = Base64.decodeBase64(strPubKey);

            PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(privKeyByte);
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(pubKeyByte);

            KeyFactory kf = KeyFactory.getInstance("RSA");

            PrivateKey privKey = kf.generatePrivate(privKeySpec);
            PublicKey pubKey = kf.generatePublic(pubKeySpec);

            byte [] encryptByte = EncryptUtil.encrypt(s, pubKey);

            System.out.println(EncryptUtil.decrypt(encryptByte, privKey));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    public void testXRsa() {

        Map<String, String> keys = XRsa.createKeys(1024);

//        System.out.println(keys);
        String piKey = "-----BEGIN PRIVATE KEY-----\n" +
                "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC3NRVU4KfDU1w2\n" +
                "bPbvOYHiPWSS1t3+SK41WH1xkALkNWkVwqFRvbol9AAB0uGOGOTdYoklawzBW2er\n" +
                "Y+Yyv1P1DMSrzlZlI9JfjGX/qIBY2ZjVnhhsf4D08rlmzWnVKd6ijWD4HdGv/T96\n" +
                "IWimFiVxc+Jwkjs7jn53NH2ygz5P/tkbKyIGs5VOK2XD0gvOnjehgu/8JW6T9zV7\n" +
                "mKrCKDt/DAE1hcO5wxl8AlsWDQQ2p/sDo/bfw967m9lHBTZACb20TLifOm16nr2F\n" +
                "kSR1i/nKlEZ2R4r4Yr6OH2EID4KuNl9Ts/YwrsBt9AgWAnxenGdXg7ExTZy9DSOB\n" +
                "Z7UpkDjbAgMBAAECggEBAKBzCP+Q0NwrwXKY85qHTs0I33AKZdkxZgCqQxKWATJD\n" +
                "5Ih+MDIaa1CZ5Pez7H46JxYMGfh/TGh62MwtbYm8LpdKW2PsDZX+TVwI5buFowIt\n" +
                "b8CF+PXspM/hU6ZI+AsL5EOQdgBWfTIYJOOdJr1uX9SAnSvhpaZ/IRrdCcixUH1V\n" +
                "QrqX8mhFHTBXbq134JRX/cAnTAUkklkKe2GBhxtGyx6117PSY34oHHyZ0XQi+GYC\n" +
                "X6CgUXr9maHVu6DciBda5fCtCSexLPZ8S2d6eT73O13tdNOU+oxbd5Rpu/Ltr32R\n" +
                "k9KK+FQ/Gkuppu5Jd1v0sd+P/nz4yf9NHDpuILAep8ECgYEA8fnQyWH4G9Zm4V+H\n" +
                "0UWKxXZJcENuNq9xM3j7ztVbMy19I/pU35OZMC6bNSU2QAmcf89sgkSIyxT24+dl\n" +
                "3EignLrQFwIOF4LEsaZfWtY64pDyddD/no0fbdE064XU0/mgHYnY2HdI00ZIsPSo\n" +
                "xtwpIjghrzY8RRbyhlmPSO3BjRMCgYEAwdNSi5in98HuBQVmesefs8mKaCtlEB4z\n" +
                "GVijR3LrueIMOM0snMhktA/OPfIs1Cg3Jg2yvriHnJnIq2lBgPMP3Vv0qDrOOMgo\n" +
                "QLMwtsINDX8jBlwyQUzjfelsVvG1yspZIKzVhOJYf6cSZWKuqQzcOGIL06S4f7yx\n" +
                "0XMWjIiZBhkCgYEAv9MdEyiZOehD8tJ6pwoMMvHM0jSCgNOgV//HD3bQL8gV9pmT\n" +
                "AxLNVAb+f6kaUrXL3P6gtLyD/QqHlrBwbasduGHYaQT2Culd6L9NxIMkd0wVChU/\n" +
                "L4AMtFAvNa8Iw2qW5hPLVf9qOiLzqWSrD8qkyT3aZj4GlXbVmHATSOtCjwUCgYAi\n" +
                "6oyuNRvdGARmxyhE6f0tCBt01IxqMOaHpF4VmvA4Fi6ZdOyaeccaYNaIdj35Ih19\n" +
                "L26JyPg1D8cEifuQ3Th4af5S5/UKaZRGw4D9C0wKqGKI3Mb42H8KyagIqZZUJ8uS\n" +
                "hVAZliTXKiO4O+S282ggiBkVUVEjQbzOvGrNejYG0QKBgQDYC0QeEkXpcLGx06ZC\n" +
                "2UyXRJ/45lfJutoqLLWFJ+uplIQrUPZYcTEeqXDyNBJYslhXMlVG2jDCv4kPBIXR\n" +
                "I4+yy82gab8p4jjRbnYEaDxYH2AwzM0VW3c7FfSsUZ2Mki1+dq56FZ1+5W671p56\n" +
                "Fn3AtB7T2r+BPrmC3wmAcAiwLA==\n" +
                "-----END PRIVATE KEY-----\n";
        String puKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
                "MIIEpQIBAAKCAQEAtzUVVOCnw1NcNmz27zmB4j1kktbd/kiuNVh9cZAC5DVpFcKh\n" +
                "Ub26JfQAAdLhjhjk3WKJJWsMwVtnq2PmMr9T9QzEq85WZSPSX4xl/6iAWNmY1Z4Y\n" +
                "bH+A9PK5Zs1p1Sneoo1g+B3Rr/0/eiFophYlcXPicJI7O45+dzR9soM+T/7ZGysi\n" +
                "BrOVTitlw9ILzp43oYLv/CVuk/c1e5iqwig7fwwBNYXDucMZfAJbFg0ENqf7A6P2\n" +
                "38Peu5vZRwU2QAm9tEy4nzptep69hZEkdYv5ypRGdkeK+GK+jh9hCA+CrjZfU7P2\n" +
                "MK7AbfQIFgJ8XpxnV4OxMU2cvQ0jgWe1KZA42wIDAQABAoIBAQCgcwj/kNDcK8Fy\n" +
                "mPOah07NCN9wCmXZMWYAqkMSlgEyQ+SIfjAyGmtQmeT3s+x+OicWDBn4f0xoetjM\n" +
                "LW2JvC6XSltj7A2V/k1cCOW7haMCLW/Ahfj17KTP4VOmSPgLC+RDkHYAVn0yGCTj\n" +
                "nSa9bl/UgJ0r4aWmfyEa3QnIsVB9VUK6l/JoRR0wV26td+CUV/3AJ0wFJJJZCnth\n" +
                "gYcbRssetdez0mN+KBx8mdF0IvhmAl+goFF6/Zmh1bug3IgXWuXwrQknsSz2fEtn\n" +
                "enk+9ztd7XTTlPqMW3eUabvy7a99kZPSivhUPxpLqabuSXdb9LHfj/58+Mn/TRw6\n" +
                "biCwHqfBAoGBAPH50Mlh+BvWZuFfh9FFisV2SXBDbjavcTN4+87VWzMtfSP6VN+T\n" +
                "mTAumzUlNkAJnH/PbIJEiMsU9uPnZdxIoJy60BcCDheCxLGmX1rWOuKQ8nXQ/56N\n" +
                "H23RNOuF1NP5oB2J2Nh3SNNGSLD0qMbcKSI4Ia82PEUW8oZZj0jtwY0TAoGBAMHT\n" +
                "UouYp/fB7gUFZnrHn7PJimgrZRAeMxlYo0dy67niDDjNLJzIZLQPzj3yLNQoNyYN\n" +
                "sr64h5yZyKtpQYDzD91b9Kg6zjjIKECzMLbCDQ1/IwZcMkFM433pbFbxtcrKWSCs\n" +
                "1YTiWH+nEmVirqkM3DhiC9OkuH+8sdFzFoyImQYZAoGBAL/THRMomTnoQ/LSeqcK\n" +
                "DDLxzNI0goDToFf/xw920C/IFfaZkwMSzVQG/n+pGlK1y9z+oLS8g/0Kh5awcG2r\n" +
                "Hbhh2GkE9grpXei/TcSDJHdMFQoVPy+ADLRQLzWvCMNqluYTy1X/ajoi86lkqw/K\n" +
                "pMk92mY+BpV21ZhwE0jrQo8FAoGAIuqMrjUb3RgEZscoROn9LQgbdNSMajDmh6Re\n" +
                "FZrwOBYumXTsmnnHGmDWiHY9+SIdfS9uicj4NQ/HBIn7kN04eGn+Uuf1CmmURsOA\n" +
                "/QtMCqhiiNzG+Nh/CsmoCKmWVCfLkoVQGZYk1yojuDvktvNoIIgZFVFRI0G8zrxq\n" +
                "zXo2BtECgYEA2AtEHhJF6XCxsdOmQtlMl0Sf+OZXybraKiy1hSfrqZSEK1D2WHEx\n" +
                "Hqlw8jQSWLJYVzJVRtowwr+JDwSF0SOPssvNoGm/KeI40W52BGg8WB9gMMzNFVt3\n" +
                "OxX0rFGdjJItfnauehWdfuVuu9aeehZ9wLQe09q/gT65gt8JgHAIsCw=\n" +
                "-----END RSA PRIVATE KEY-----\n";

//        XRsa rsa = new XRsa(keys.get("publicKey"), keys.get("privateKey"));
        XRsa rsa = new XRsa(puKey, piKey);
        String data = "hello world";

        String encrypted = rsa.privateEncrypt(data);
        String decrypted = rsa.publicDecrypt(encrypted);


        System.out.println(encrypted);
        System.out.println(decrypted);
    }
}
