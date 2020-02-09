package com.yuyakaido.android.gaia

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yuyakaido.android.gaia.article.ArticleApi
import com.yuyakaido.android.gaia.auth.AuthApi
import com.yuyakaido.android.gaia.auth.AuthInterceptor
import com.yuyakaido.android.gaia.auth.BasicAuthInterceptor
import com.yuyakaido.android.gaia.auth.TokenAuthenticator
import com.yuyakaido.android.gaia.comment.CommentApi
import com.yuyakaido.android.gaia.community.CommunityApi
import com.yuyakaido.android.gaia.core.domain.app.AppScope
import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.*
import com.yuyakaido.android.gaia.search.SearchApi
import com.yuyakaido.android.gaia.user.infrastructure.remote.UserApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule {

  private fun createBaseOkHttpClientBuilder(): OkHttpClient.Builder {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
      .apply { level = HttpLoggingInterceptor.Level.BASIC }
    return OkHttpClient
      .Builder()
      .addNetworkInterceptor(StethoInterceptor())
      .addInterceptor(httpLoggingInterceptor)
  }

  @AppScope
  @OkHttpForPublic
  @Provides
  fun provideOkHttpClientForPublic(): OkHttpClient {
    return createBaseOkHttpClientBuilder()
      .addInterceptor(BasicAuthInterceptor())
      .build()
  }

  @AppScope
  @OkHttpForPrivate
  @Provides
  fun provideOkHttpClientForPrivate(
    repository: TokenRepositoryType
  ): OkHttpClient {
    return createBaseOkHttpClientBuilder()
      .addInterceptor(
        AuthInterceptor(
          repository = repository
        )
      )
      .authenticator(
        TokenAuthenticator(
          repository = repository
        )
      )
      .build()
  }

  @AppScope
  @Provides
  fun provideMoshi(): Moshi {
    return Moshi
      .Builder()
      .add(
        PolymorphicJsonAdapterFactory
          .of(ListingDataResponse.Children.Child::class.java, "kind")
          .withSubtype(
            ListingDataResponse.Children.Child.Comment::class.java,
            Kind.Comment.id
          )
          .withSubtype(
            ListingDataResponse.Children.Child.Article::class.java,
            Kind.Article.id
          )
          .withSubtype(
            ListingDataResponse.Children.Child.Community::class.java,
            Kind.Community.id
          )
          .withSubtype(
            ListingDataResponse.Children.Child.More::class.java,
            Kind.More.id
          )
      )
      .add(KotlinJsonAdapterFactory())
      .build()
  }

  @AppScope
  @RetrofitForPublic
  @Provides
  fun provideRetrofitForPublic(
    @OkHttpForPublic client: OkHttpClient,
    moshi: Moshi
  ): Retrofit {
    return Retrofit
      .Builder()
      .client(client)
      .baseUrl("https://www.reddit.com/")
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()
  }

  @AppScope
  @RetrofitForPrivate
  @Provides
  fun provideRetrofitForPrivate(
    @OkHttpForPrivate client: OkHttpClient,
    moshi: Moshi
  ): Retrofit {
    return Retrofit
      .Builder()
      .client(client)
      .baseUrl("https://oauth.reddit.com")
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()
  }

  @AppScope
  @Provides
  fun provideArticleApi(
    moshi: Moshi,
    @OkHttpForPrivate client: OkHttpClient
  ): ArticleApi {
    val retrofit = Retrofit
      .Builder()
      .client(client)
      .baseUrl("https://oauth.reddit.com")
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()
    return retrofit.create(ArticleApi::class.java)
  }

  @AppScope
  @Provides
  fun provideCommunityApi(
    moshi: Moshi,
    @OkHttpForPrivate client: OkHttpClient
  ): CommunityApi {
    val retrofit = Retrofit
      .Builder()
      .client(client)
      .baseUrl("https://oauth.reddit.com")
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()
    return retrofit.create(CommunityApi::class.java)
  }

  @AppScope
  @Provides
  fun provideCommentApi(
    moshi: Moshi,
    @OkHttpForPrivate client: OkHttpClient
  ): CommentApi {
    val retrofit = Retrofit
      .Builder()
      .client(client)
      .baseUrl("https://oauth.reddit.com")
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()
    return retrofit.create(CommentApi::class.java)
  }

  @AppScope
  @Provides
  fun provideUserApi(
    moshi: Moshi,
    @OkHttpForPrivate client: OkHttpClient
  ): UserApi {
    val retrofit = Retrofit
      .Builder()
      .client(client)
      .baseUrl("https://oauth.reddit.com")
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()
    return retrofit.create(UserApi::class.java)
  }

  @AppScope
  @Provides
  fun provideSearchApi(
    moshi: Moshi,
    @OkHttpForPublic client: OkHttpClient
  ): SearchApi {
    val retrofit = Retrofit
      .Builder()
      .client(client)
      .baseUrl("https://www.reddit.com/")
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()
    return retrofit.create(SearchApi::class.java)
  }

}