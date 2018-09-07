package com.mmlogs.runshell.util;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Class EncryptUtil
 *
 * @author Gene.yang
 * @date 2018/07/16
 */
public class EncryptUtil {

    /**
     * String to hold name of the encryption algorithm.
     */
    private static final String ALGORITHM = "RSA";

    /**
     * String to hold the name of the private key file.
     */
    public static final String PRIVATE_KEY_FILE = "D:/rsa/pkcs8_priv.pem";

    /**
     * String to hold name of the public key file.
     */
    public static final String PUBLIC_KEY_FILE = "D:/rsa/public.key";

    /**
     * Encrypt the plain text using public key.
     *
     * @param text
     *            : original plain text
     * @param key
     *            :The public key
     * @return Encrypted text
     */
    public static byte[] encrypt(String text, PublicKey key) {
        byte[] cipherText = null;
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            // encrypt the plain text using the public key
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherText;
    }

    /**
     * Decrypt text using private key.
     *
     * @param text
     *            :encrypted text
     * @param key
     *            :The private key
     * @return plain text
     */
    public static String decrypt(byte[] text, PrivateKey key) {
        byte[] dectyptedText = null;
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance(ALGORITHM);

            // decrypt the text using the private key
            cipher.init(Cipher.DECRYPT_MODE, key);
            dectyptedText = cipher.doFinal(text);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        assert dectyptedText != null;
        return new String(dectyptedText);
    }
}
