package com.yuyakaido.android.gaia.github.infra.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.yuyakaido.android.gaia.github.infra.GithubClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class ClientModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.github.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    fun provideGithubService(
        retrofit: Retrofit
    ): GithubClient.GithubService {
        return retrofit.create(GithubClient.GithubService::class.java)
    }

}