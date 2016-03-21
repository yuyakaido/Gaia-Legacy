package com.yuyakaido.android.genesis.infra.client.common;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by yuyakaido on 2/15/16.
 */
public class HttpClient {

    private static OkHttpClient instance;

    private HttpClient() {}

    public static synchronized OkHttpClient getInstance() {
        if (instance == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            instance = new OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .addNetworkInterceptor(new StethoInterceptor())
            .build();
        }

        return instance;
    }

}
