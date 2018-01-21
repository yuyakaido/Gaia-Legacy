package com.yuyakaido.android.blueprint.infra

import android.util.Log
import com.twitter.sdk.android.core.TwitterSession
import com.yuyakaido.android.blueprint.BuildConfig
import com.yuyakaido.android.blueprint.domain.Tweet
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor

class TwitterClient {

    private fun createRetrofit(session: TwitterSession): Retrofit {
        val consumer = OkHttpOAuthConsumer(
                BuildConfig.TWITTER_CONSUMER_KEY,
                BuildConfig.TWITTER_CONSUMER_SECRET)
        consumer.setTokenWithSecret(session.authToken.token, session.authToken.secret)
        val client = OkHttpClient.Builder()
                .addInterceptor(SigningInterceptor(consumer))
                .build()
        return Retrofit.Builder()
                .baseUrl("https://api.twitter.com/1.1/")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun homeTimeline(session: TwitterSession): Observable<List<Tweet>> {
        return createRetrofit(session)
                .create(TwitterService::class.java)
                .homeTimeline(session.userName)
                .doOnError { Log.e("Blueprint", it.toString()) }
    }

    interface TwitterService {
        @GET("statuses/home_timeline.json")
        fun homeTimeline(@Query("screen_name") screenName: String): Observable<List<Tweet>>
    }

}