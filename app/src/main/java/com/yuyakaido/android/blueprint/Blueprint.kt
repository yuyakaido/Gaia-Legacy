package com.yuyakaido.android.blueprint

import android.app.Application
import com.twitter.sdk.android.core.*

class Blueprint : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeTwitter()
    }

    private fun initializeTwitter() {
        Twitter.initialize(TwitterConfig.Builder(this)
                .debug(BuildConfig.DEBUG)
                .twitterAuthConfig(TwitterAuthConfig(
                        BuildConfig.TWITTER_CONSUMER_KEY,
                        BuildConfig.TWITTER_CONSUMER_SECRET))
                .build())

        val token = TwitterAuthToken(
                BuildConfig.TWITTER_ACCESS_TOKEN,
                BuildConfig.TWITTER_ACCESS_TOKEN_SECRET)
        val client = TwitterApiClient(
                TwitterSession(token, 424206971, "yuyakaido"))
        TwitterCore.getInstance().addGuestApiClient(client)
    }

}