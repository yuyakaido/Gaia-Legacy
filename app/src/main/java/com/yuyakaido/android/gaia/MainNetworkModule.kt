package com.yuyakaido.android.gaia

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yuyakaido.android.gaia.auth.AuthInterceptor
import com.yuyakaido.android.gaia.auth.BasicAuthInterceptor
import com.yuyakaido.android.gaia.auth.TokenAuthenticator
import com.yuyakaido.android.gaia.core.domain.entity.Session
import com.yuyakaido.android.gaia.core.domain.repository.SessionRepositoryType
import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.Kind
import com.yuyakaido.android.gaia.core.infrastructure.ListingDataResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

abstract class MainNetworkModule {

  private fun createBaseOkHttpClientBuilder(): OkHttpClient.Builder {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
      .apply { level = HttpLoggingInterceptor.Level.BASIC }
    return OkHttpClient
      .Builder()
      .addNetworkInterceptor(StethoInterceptor())
      .addInterceptor(httpLoggingInterceptor)
  }

  private fun createOkHttpClientForPublic(): OkHttpClient {
    return createBaseOkHttpClientBuilder()
      .addInterceptor(BasicAuthInterceptor())
      .build()
  }

  private fun createOkHttpClientForPrivate(
    session: Session,
    sessionRepository: SessionRepositoryType,
    tokenRepository: TokenRepositoryType
  ): OkHttpClient {
    return createBaseOkHttpClientBuilder()
      .addInterceptor(
        AuthInterceptor(
          initial = session,
          repository = sessionRepository
        )
      )
      .authenticator(
        TokenAuthenticator(
          initial = session,
          sessionRepository = sessionRepository,
          tokenRepository = tokenRepository
        )
      )
      .build()
  }

  private fun createBaseMoshi(): Moshi {
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

  protected fun createRetrofitForPublic(): Retrofit {
    return Retrofit
      .Builder()
      .client(createOkHttpClientForPublic())
      .baseUrl("https://www.reddit.com/")
      .addConverterFactory(MoshiConverterFactory.create(createBaseMoshi()))
      .build()
  }

  protected fun createRetrofitForPrivate(
    session: Session,
    sessionRepository: SessionRepositoryType,
    tokenRepository: TokenRepositoryType
  ): Retrofit {
    return Retrofit
      .Builder()
      .client(
        createOkHttpClientForPrivate(
          session = session,
          sessionRepository = sessionRepository,
          tokenRepository = tokenRepository
        )
      )
      .baseUrl("https://oauth.reddit.com")
      .addConverterFactory(MoshiConverterFactory.create(createBaseMoshi()))
      .build()
  }

}