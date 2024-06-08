package com.iflytek.study.ota;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.UUID;

public class DirtyApiClient {

    private static final OkHttpClient client = new OkHttpClient(); // 静态实例，可重用

    public Response execute(Request request) {
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            // 处理异常，例如记录日志或返回错误响应
            e.printStackTrace();
            return null; // 或者抛出异常，或者返回一个表示错误的Response对象
        }
    }
}