package com.banxia.test;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/8/11 22:42
 */
public class ApiTest {

    @Test
    public void doGet1() throws IOException {
        // 使用OkHttpClient发送GET请求
        OkHttpClient okHttpClient = new OkHttpClient();
        // 创建一个Request对象，指定请求的URL和请求方法
        final Request request = new Request.Builder()
                .url("http://www.4399.com")
                .get()//默认就是GET请求，可以不写
                .build();
        // 使用OkHttpClient执行请求，并获取响应
        Response response = okHttpClient.newCall(request).execute();
        String string = response.body().string();
        System.out.println(string);

    }
}


