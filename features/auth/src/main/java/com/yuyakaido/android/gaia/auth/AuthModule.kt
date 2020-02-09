package com.yuyakaido.android.gaia.auth

import android.app.Application
import com.yuyakaido.android.gaia.core.domain.app.AppScope
import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPublic
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AuthModule {

  @AppScope
  @Provides
  fun provideAuthApi(
    @RetrofitForPublic retrofit: Retrofit
  ): AuthApi {
    return retrofit.create(AuthApi::class.java)
  }

  @AppScope
  @Provides
  fun provideTokenRepositoryType(
    application: Application,
    api: AuthApi
  ): TokenRepositoryType {
    return TokenRepository(
      application = application,
      api = api
    )
  }

}