package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.core.Environment
import com.yuyakaido.android.gaia.core.GithubRetrofit
import com.yuyakaido.android.gaia.foo.data.GithubClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ClientModule {

    @GithubRetrofit
    @Provides
    fun provideGithubRetrofit(env: Environment): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(env.githubApiEndpoint)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideGithubService(
        @GithubRetrofit retrofit: Retrofit
    ): GithubClient.GithubService {
        return retrofit.create(GithubClient.GithubService::class.java)
    }

}