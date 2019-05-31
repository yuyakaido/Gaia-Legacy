package com.yuyakaido.android.gaia.auth.infra

import com.yuyakaido.android.gaia.core.java.AuthRetrofit
import com.yuyakaido.android.gaia.core.java.Environment
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
    fun provideRetrofit(env: Environment): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(env.githubAuthEndpoint)
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