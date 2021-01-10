package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.core.SignedOutComponent
import com.yuyakaido.android.gaia.core.domain.app.SignedOutScope
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPublic
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit

@InstallIn(SignedOutComponent::class)
@Module
class SignedOutNetworkModule : MainNetworkModule() {

  @SignedOutScope
  @RetrofitForPublic
  @Provides
  fun provideRetrofitForPublic(): Retrofit {
    return createRetrofitForPublic()
  }

}