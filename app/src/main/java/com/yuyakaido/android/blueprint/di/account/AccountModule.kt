package com.yuyakaido.android.blueprint.di.account

import android.app.Application
import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.yuyakaido.android.blueprint.BuildConfig
import com.yuyakaido.android.blueprint.domain.Account
import com.yuyakaido.android.blueprint.infra.AccountPreference
import com.yuyakaido.android.blueprint.infra.TwitterClient
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor

@Module
class AccountModule(
        private val application: Application,
        private val account: Account) {

    @AccountScope
    @Provides
    fun provideAccountPreference(): AccountPreference {
        return AccountPreference(application.getSharedPreferences(
                account.twitter.userId.toString(), Context.MODE_PRIVATE))
    }

    @AccountScope
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @AccountScope
    @Provides
    fun provideOkHttpClinet(): OkHttpClient {
        val consumer = OkHttpOAuthConsumer(
                BuildConfig.TWITTER_CONSUMER_KEY,
                BuildConfig.TWITTER_CONSUMER_SECRET)
        consumer.setTokenWithSecret(account.twitter.authToken.token, account.twitter.authToken.secret)
        return OkHttpClient.Builder()
                .addInterceptor(SigningInterceptor(consumer))
                .addInterceptor(HttpLoggingInterceptor()
                        .apply { level = HttpLoggingInterceptor.Level.BASIC })
                .addNetworkInterceptor(StethoInterceptor())
                .build()
    }

    @AccountScope
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://api.twitter.com/1.1/")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @AccountScope
    @Provides
    fun provideTwitterService(retrofit: Retrofit): TwitterClient.TwitterService {
        return retrofit.create(TwitterClient.TwitterService::class.java)
    }

}