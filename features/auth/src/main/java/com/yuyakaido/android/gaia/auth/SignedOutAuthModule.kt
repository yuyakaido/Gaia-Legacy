package com.yuyakaido.android.gaia.auth

import com.yuyakaido.android.gaia.core.domain.app.SignedOutScope
import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPublic
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class SignedOutAuthModule {

  @SignedOutScope
  @Provides
  fun provideAuthApi(
    @RetrofitForPublic retrofit: Retrofit
  ): AuthApi {
    return retrofit.create(AuthApi::class.java)
  }

  @SignedOutScope
  @Provides
  fun provideTokenRepositoryType(
    api: AuthApi
  ): TokenRepositoryType {
    return TokenRepository(
      api = api
    )
  }

}