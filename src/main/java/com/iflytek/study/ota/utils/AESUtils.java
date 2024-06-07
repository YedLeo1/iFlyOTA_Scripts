package com.iflytek.study.ota.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AESUtils {

    private static SecretKeySpec generatorKeySpec(byte[] bArr) {
        if (bArr.length == 0) {
            return null;
        }
        try {
            return new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("SHA-1").digest(bArr), 16), "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getKey() {
        StringBuilder sb = new StringBuilder("dWe4XiomNjdV");
        sb.replace(2, 3, "Q");
        return new String(Base64Utils.decode(sb.toString()));
    }

    public static byte[] decrypt(byte[] encryptedData) {
        byte[] keyBytes = getKey().getBytes(StandardCharsets.UTF_8);

        if (encryptedData.length != 0 && keyBytes.length != 0) {
            try {
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
                cipher.init(2, generatorKeySpec(keyBytes));
                return cipher.doFinal(encryptedData);
            } catch (Exception e) {
                System.out.println("Error while AES decrypting: " + e.toString());
            }
        }
        return null;
    }

    public static byte[] encrypt(byte[] DataToEncrypt) {
        byte[] keyBytes = getKey().getBytes(StandardCharsets.UTF_8);

        if (DataToEncrypt.length != 0 && keyBytes.length != 0) {
            try {
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
                cipher.init(1, generatorKeySpec(keyBytes));
                return cipher.doFinal(DataToEncrypt);
            } catch (Exception e) {
                System.out.println("Error while AES encrypting: " + e.toString());
            }
        }
        return null;
    }

}
