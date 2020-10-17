package com.yuyakaido.android.gaia

import android.app.Application
import com.yuyakaido.android.gaia.auth.AuthInterceptor
import com.yuyakaido.android.gaia.auth.BasicAuthInterceptor
import com.yuyakaido.android.gaia.auth.TokenAuthenticator
import com.yuyakaido.android.gaia.core.domain.app.ImageLoaderType
import com.yuyakaido.android.gaia.core.domain.app.SignedInScope
import com.yuyakaido.android.gaia.core.domain.entity.Session
import com.yuyakaido.android.gaia.core.domain.repository.SessionRepositoryType
import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class SignedInNetworkModule : MainNetworkModule() {

  @SignedInScope
  @OkHttpForPublic
  @Provides
  fun provideOkHttpClientForPublic(): OkHttpClient {
    return createBaseOkHttpClientBuilder()
      .addInterceptor(BasicAuthInterceptor())
      .build()
  }

  @SignedInScope
  @RetrofitForPublic
  @Provides
  fun provideRetrofitForPublic(
    @OkHttpForPublic client: OkHttpClient
  ): Retrofit {
    return Retrofit
      .Builder()
      .client(client)
      .baseUrl("https://www.reddit.com/")
      .addConverterFactory(MoshiConverterFactory.create(createBaseMoshi()))
      .build()
  }

  @SignedInScope
  @OkHttpForPrivate
  @Provides
  fun provideOkHttpClientForPrivate(
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

  @SignedInScope
  @RetrofitForPrivate
  @Provides
  fun provideRetrofitForPrivate(
    @OkHttpForPrivate client: OkHttpClient
  ): Retrofit {
    return Retrofit
      .Builder()
      .client(client)
      .baseUrl("https://oauth.reddit.com")
      .addConverterFactory(MoshiConverterFactory.create(createBaseMoshi()))
      .build()
  }

  @SignedInScope
  @Provides
  fun provideImageLoaderType(
    application: Application
  ): ImageLoaderType {
    return ImageLoader(
      application = application
    )
  }

}