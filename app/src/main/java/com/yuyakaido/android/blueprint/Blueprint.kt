package com.yuyakaido.android.blueprint

import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import com.yuyakaido.android.blueprint.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class Blueprint : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        initializeTwitter()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().build()
    }

    private fun initializeTwitter() {
        Twitter.initialize(TwitterConfig.Builder(this)
                .debug(BuildConfig.DEBUG)
                .twitterAuthConfig(TwitterAuthConfig(
                        BuildConfig.TWITTER_CONSUMER_KEY,
                        BuildConfig.TWITTER_CONSUMER_SECRET))
                .build())
    }

}