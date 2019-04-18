package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.GithubClient
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ServiceModule {

    @Provides
    fun provideGithubService(retrofit: Retrofit): GithubClient.GithubService {
        return retrofit.create(GithubClient.GithubService::class.java)
    }

}