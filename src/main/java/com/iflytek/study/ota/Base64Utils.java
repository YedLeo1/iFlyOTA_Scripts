package com.iflytek.study.ota;

import java.util.Base64;

public class Base64Utils {
    public static byte[] decode(String encodedString) {
        try {
            // 使用Base64类的getDecoder()方法获取解码器，并使用其decode()方法进行解码
            // 注意：Base64.getDecoder().decode()返回的是byte[]类型，你需要使用新的String构造函数将其转换为字符串
            // 假设原始数据是UTF-8编码
            return Base64.getDecoder().decode(encodedString);
        } catch (IllegalArgumentException e) {
            // 当输入不是有效的Base64字符串时，抛出此异常
            System.err.println("Error decoding Base64 string: " + e.getMessage());
            return null;
        }
    }

    public static String encode(byte[] dataToEncode) {
        try {
            return Base64.getEncoder().encodeToString(dataToEncode);
        } catch (IllegalArgumentException e) {
            System.err.println("Error encoding Base64 string: " + e.getMessage());
            return null;
        }
    }
}
