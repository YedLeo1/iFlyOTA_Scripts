package com.iflytek.study.ota;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.util.Arrays;

public class DESUtils {

    private static final String KEY = "15d8e25cb9d6b73bf6bbb9dc417d82c";

    public static String getKey() {
        byte[] keyBytes = Arrays.copyOfRange(KEY.getBytes(), 0, 8);
        return new String(keyBytes);
    }

    public static byte[] decrypt(byte[] encryptedData) throws Exception {
        byte[] keyBytes = Arrays.copyOfRange(KEY.getBytes(), 0, 8);

        // 初始化DES密钥规范
        DESKeySpec dESKeySpec = new DESKeySpec(keyBytes);

        // 生成密钥
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = secretKeyFactory.generateSecret(dESKeySpec);

        // 初始化Cipher实例进行解密
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        // 执行解密
        return cipher.doFinal(encryptedData);
    }

    public static byte[] encrypt(byte[] dataToEncrypt) throws Exception {
        byte[] keyBytes = Arrays.copyOfRange(KEY.getBytes(), 0, 8);

        DESKeySpec dESKeySpec = new DESKeySpec(Arrays.copyOf(keyBytes, 8)); // 使用前8个字节作为密钥
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = secretKeyFactory.generateSecret(dESKeySpec);

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(dataToEncrypt);
    }

}
