package com.yuyakaido.android.gaia

import android.app.Application
import com.yuyakaido.android.gaia.auth.AccessTokenService
import com.yuyakaido.android.gaia.core.domain.app.AccessTokenServiceType
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.app.AppScope
import dagger.Module
import dagger.Provides

@Module
class AppModule {

  @AppScope
  @Provides
  fun provideAppRouterType(
    application: Application
  ): AppRouterType {
    return AppRouter(application = application)
  }

  @AppScope
  @Provides
  fun provideAccessTokenServiceType(
    application: Application
  ): AccessTokenServiceType {
    return AccessTokenService(application = application)
  }

}