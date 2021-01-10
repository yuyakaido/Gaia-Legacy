package com.yuyakaido.android.gaia.auth

import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import retrofit2.Retrofit

abstract class MainAuthModule {

  private fun createAuthApi(retrofit: Retrofit): AuthApi {
    return retrofit.create(AuthApi::class.java)
  }

  fun createTokenRepositoryType(
    retrofit: Retrofit
  ): TokenRepositoryType {
    return TokenRepository(
      api = createAuthApi(
        retrofit = retrofit
      )
    )
  }

}