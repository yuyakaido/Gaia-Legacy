package com.yuyakaido.android.gaia.auth

import com.yuyakaido.android.gaia.core.SignedOutComponent
import com.yuyakaido.android.gaia.core.domain.app.SignedOutScope
import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPublic
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit

@InstallIn(SignedOutComponent::class)
@Module
class SignedOutAuthModule : MainAuthModule() {

  @SignedOutScope
  @Provides
  fun provideTokenRepositoryType(
    @RetrofitForPublic retrofit: Retrofit
  ): TokenRepositoryType {
    return createTokenRepositoryType(
      retrofit = retrofit
    )
  }

}