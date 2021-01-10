package com.yuyakaido.android.gaia.auth

import com.yuyakaido.android.gaia.core.SigningInComponent
import com.yuyakaido.android.gaia.core.domain.app.SigningInScope
import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPublic
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit

@InstallIn(SigningInComponent::class)
@Module
class SigningInAuthModule : MainAuthModule() {

  @SigningInScope
  @Provides
  fun provideTokenRepositoryType(
    @RetrofitForPublic retrofit: Retrofit
  ): TokenRepositoryType {
    return createTokenRepositoryType(
      retrofit = retrofit
    )
  }

}