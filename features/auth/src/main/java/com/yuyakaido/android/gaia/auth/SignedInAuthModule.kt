package com.yuyakaido.android.gaia.auth

import com.yuyakaido.android.gaia.core.SignedInComponent
import com.yuyakaido.android.gaia.core.domain.app.SignedInScope
import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPublic
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit

@InstallIn(SignedInComponent::class)
@Module
class SignedInAuthModule : MainAuthModule() {

  @SignedInScope
  @Provides
  fun provideTokenRepositoryType(
    @RetrofitForPublic retrofit: Retrofit
  ): TokenRepositoryType {
    return createTokenRepositoryType(
      retrofit = retrofit
    )
  }

}