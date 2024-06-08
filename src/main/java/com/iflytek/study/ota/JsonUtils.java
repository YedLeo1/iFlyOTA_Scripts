package com.iflytek.study.ota;

public class JsonUtils {

    public static byte[] json2Bytes(String jsonString) {
        // 将JSON字符串转换为字节数组
        return (jsonString != null) ? jsonString.getBytes() : null;
    }

    public static String bytes2Json(byte[] bArr) {
        return (bArr != null) ? new String(bArr) : null;
    }

}