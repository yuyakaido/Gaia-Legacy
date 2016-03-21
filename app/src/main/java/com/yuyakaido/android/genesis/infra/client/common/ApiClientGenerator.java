package com.yuyakaido.android.genesis.infra.client.common;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yuyakaido on 2/15/16.
 */
public class ApiClientGenerator {

    public static <T> T generate(Class<T> clazz, String baseUrl) {
        return new Retrofit.Builder()
                .client(HttpClient.getInstance())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(clazz);
    }

}
