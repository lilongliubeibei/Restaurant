package com.example.yiliedurestaurant.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    private static OkHttpClient okHttpClient = null;

    public HttpUtil() {
    }

    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            //同步锁
            synchronized (HttpUtil.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient();
                }
            }
        }
        return okHttpClient;
    }

    public static void OkHttpRequestGet(String address,okhttp3.Callback callback){
        OkHttpClient client=getInstance();
        Request request=new Request.Builder().url(address).addHeader("Content-Type","application/x-www-form-urlencoded").build();
        client.newCall(request).enqueue(callback);
    }
    public static void OkHttpRequestPost(String address, RequestBody body,okhttp3.Callback callback){
        OkHttpClient client=getInstance();
        Request request=new Request.Builder().url(address).post(body).addHeader("Content-Type","application/x-www-form-urlencoded").build();
        client.newCall(request).enqueue(callback);
    }
}
