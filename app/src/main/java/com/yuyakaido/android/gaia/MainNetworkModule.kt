package com.yuyakaido.android.gaia

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.yuyakaido.android.gaia.auth.AuthInterceptor
import com.yuyakaido.android.gaia.auth.BasicAuthInterceptor
import com.yuyakaido.android.gaia.auth.TokenAuthenticator
import com.yuyakaido.android.gaia.core.domain.entity.Session
import com.yuyakaido.android.gaia.core.domain.repository.SessionRepositoryType
import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.Kind
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

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

  @ExperimentalSerializationApi
  protected fun createRetrofitForPublic(): Retrofit {
    return Retrofit
      .Builder()
      .client(createOkHttpClientForPublic())
      .baseUrl("https://www.reddit.com/")
      .addConverterFactory(Json {
        ignoreUnknownKeys = true
        classDiscriminator = Kind.classDiscriminator
      }.asConverterFactory("application/json".toMediaType()))
      .build()
  }

  @ExperimentalSerializationApi
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
      .addConverterFactory(Json {
        ignoreUnknownKeys = true
        classDiscriminator = Kind.classDiscriminator
      }.asConverterFactory("application/json".toMediaType()))
      .build()
  }

}