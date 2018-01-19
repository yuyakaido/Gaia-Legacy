package com.yuyakaido.android.blueprint.di

import com.twitter.sdk.android.core.TwitterApiClient
import com.twitter.sdk.android.core.TwitterAuthToken
import com.twitter.sdk.android.core.TwitterSession
import com.yuyakaido.android.blueprint.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideTwitterApiClient(): TwitterApiClient {
        val token = TwitterAuthToken(
                BuildConfig.TWITTER_ACCESS_TOKEN,
                BuildConfig.TWITTER_ACCESS_TOKEN_SECRET)
        return TwitterApiClient(TwitterSession(token, 424206971, "yuyakaido"))
    }

}