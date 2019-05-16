package com.yuyakaido.android.gaia.auth.infra.di

import com.yuyakaido.android.gaia.auth.infra.AuthClient
import com.yuyakaido.android.gaia.core.AuthRetrofit
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AuthClientModule {

    @AuthRetrofit
    @Provides
    fun provideRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://github.com/login/oauth/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideAuthService(
        @AuthRetrofit retrofit: Retrofit
    ): AuthClient.AuthService {
        return retrofit.create(AuthClient.AuthService::class.java)
    }

}