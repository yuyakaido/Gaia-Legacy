package com.yuyakaido.android.gaia.auth

import com.yuyakaido.android.gaia.core.domain.app.SignedInScope
import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPublic
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class SignedInAuthModule {

  @SignedInScope
  @Provides
  fun provideAuthApi(
    @RetrofitForPublic retrofit: Retrofit
  ): AuthApi {
    return retrofit.create(AuthApi::class.java)
  }

  @SignedInScope
  @Provides
  fun provideTokenRepositoryType(
    api: AuthApi
  ): TokenRepositoryType {
    return TokenRepository(
      api = api
    )
  }

}