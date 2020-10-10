package com.yuyakaido.android.gaia

import android.app.Application
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.domain.app.AppScope
import com.yuyakaido.android.gaia.core.presentation.AppNavigatorType
import com.yuyakaido.android.gaia.support.ReleaseSupportNotificationManager
import com.yuyakaido.android.gaia.support.SupportNotificationManager
import dagger.Module
import dagger.Provides

@Module
class AppModule : MainAppModule() {

  @AppScope
  @Provides
  fun provideSupportNotificationManager(
    application: Application,
    appNavigator: AppNavigatorType,
    appStore: AppStore
  ): SupportNotificationManager {
    return ReleaseSupportNotificationManager(
      application = application,
      appNavigator = appNavigator,
      appStore = appStore
    )
  }

}