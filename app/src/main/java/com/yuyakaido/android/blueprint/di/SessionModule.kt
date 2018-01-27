package com.yuyakaido.android.blueprint.di

import com.twitter.sdk.android.core.TwitterSession
import com.yuyakaido.android.blueprint.BuildConfig
import com.yuyakaido.android.blueprint.infra.TwitterClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor

@Module
class SessionModule(private val session: TwitterSession) {

    @SessionScope
    @Provides
    fun provideRetrofit(): Retrofit {
        val consumer = OkHttpOAuthConsumer(
                BuildConfig.TWITTER_CONSUMER_KEY,
                BuildConfig.TWITTER_CONSUMER_SECRET)
        consumer.setTokenWithSecret(session.authToken.token, session.authToken.secret)
        val client = OkHttpClient.Builder()
                .addInterceptor(SigningInterceptor(consumer))
                .addInterceptor(HttpLoggingInterceptor()
                        .apply { level = HttpLoggingInterceptor.Level.BASIC })
                .build()
        return Retrofit.Builder()
                .baseUrl("https://api.twitter.com/1.1/")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @SessionScope
    @Provides
    fun provideTwitterService(retrofit: Retrofit): TwitterClient.TwitterService {
        return retrofit.create(TwitterClient.TwitterService::class.java)
    }

}