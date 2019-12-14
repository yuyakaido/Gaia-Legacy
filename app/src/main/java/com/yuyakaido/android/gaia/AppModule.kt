package com.yuyakaido.android.gaia

import android.app.Application
import com.yuyakaido.android.gaia.core.app.AppRouterType
import com.yuyakaido.android.gaia.core.app.AppScope
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

}