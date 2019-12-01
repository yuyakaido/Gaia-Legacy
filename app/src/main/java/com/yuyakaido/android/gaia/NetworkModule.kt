package com.yuyakaido.android.gaia

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yuyakaido.android.gaia.core.AuthInterceptor
import com.yuyakaido.android.gaia.core.RedditService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule {

  @Provides
  fun provideOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
      .apply { level = HttpLoggingInterceptor.Level.BASIC }
    val authInterceptor = AuthInterceptor()
    return OkHttpClient
      .Builder()
      .addInterceptor(httpLoggingInterceptor)
      .addInterceptor(authInterceptor)
      .build()
  }

  @Provides
  fun provideRetrofit(client: OkHttpClient): Retrofit {
    val moshi = Moshi
      .Builder()
      .add(KotlinJsonAdapterFactory())
      .build()
    return Retrofit
      .Builder()
      .client(client)
      .baseUrl("https://oauth.reddit.com")
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()
  }

  @Provides
  fun provideRedditService(retrofit: Retrofit): RedditService {
    return retrofit.create(RedditService::class.java)
  }

}