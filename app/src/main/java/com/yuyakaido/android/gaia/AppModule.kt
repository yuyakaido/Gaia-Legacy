package com.yuyakaido.android.gaia

import android.app.Application
import com.yuyakaido.android.gaia.core.domain.app.AppNavigatorType
import com.yuyakaido.android.gaia.core.domain.app.AppScope
import dagger.Module
import dagger.Provides

@Module
class AppModule {

  @AppScope
  @Provides
  fun provideAppNavigatorType(
    application: Application
  ): AppNavigatorType {
    return AppNavigator(
      application = application
    )
  }

}