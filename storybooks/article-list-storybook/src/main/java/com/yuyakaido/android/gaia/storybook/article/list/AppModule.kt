package com.yuyakaido.android.gaia.storybook.article.list

import android.app.Application
import com.yuyakaido.android.gaia.core.domain.app.*
import dagger.Module
import dagger.Provides

@Module
class AppModule {

  @AppScope
  @Provides
  fun provideAppRouterType(
    application: Application
  ): AppRouterType {
    return NoopAppRouter(application = application)
  }

  @AppScope
  @Provides
  fun provideAuthTokenServiceType(
    application: Application
  ): AuthTokenServiceType {
    return NoopAuthTokenService(application = application)
  }

}