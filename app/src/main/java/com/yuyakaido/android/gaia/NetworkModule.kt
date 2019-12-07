package com.yuyakaido.android.gaia

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yuyakaido.android.gaia.core.*
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
  fun provideMoshi(): Moshi {
    return Moshi
      .Builder()
      .add(
        PolymorphicJsonAdapterFactory
          .of(ListingDataResponse.Children.Child::class.java, "kind")
          .withSubtype(
            ListingDataResponse.Children.Child.Comment::class.java,
            ListingDataResponse.Children.Child.Kind.Comment.id
          )
          .withSubtype(
            ListingDataResponse.Children.Child.Article::class.java,
            ListingDataResponse.Children.Child.Kind.Article.id
          )
      )
      .add(KotlinJsonAdapterFactory())
      .build()
  }

  @Provides
  fun provideRedditPublicService(
    moshi: Moshi,
    client: OkHttpClient
  ): RedditPublicService {
    val retrofit = Retrofit
      .Builder()
      .client(client)
      .baseUrl("https://reddit.com")
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()
    return retrofit.create(RedditPublicService::class.java)
  }

  @Provides
  fun provideRedditAuthService(
    moshi: Moshi,
    client: OkHttpClient
  ): RedditAuthService {
    val retrofit = Retrofit
      .Builder()
      .client(client)
      .baseUrl("https://oauth.reddit.com")
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()
    return retrofit.create(RedditAuthService::class.java)
  }

}