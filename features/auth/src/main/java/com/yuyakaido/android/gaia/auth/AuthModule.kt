package com.yuyakaido.android.gaia.auth

import com.yuyakaido.android.gaia.core.domain.app.AppScope
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

}