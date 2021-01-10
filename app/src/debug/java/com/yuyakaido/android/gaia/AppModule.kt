package com.yuyakaido.android.gaia

import android.app.Application
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.presentation.AppNavigatorType
import com.yuyakaido.android.gaia.support.DebugSupportNotificationManager
import com.yuyakaido.android.gaia.support.SupportNotificationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

  @Singleton
  @Provides
  fun provideSupportNotificationManager(
    application: Application,
    appNavigator: AppNavigatorType,
    appStore: AppStore
  ): SupportNotificationManager {
    return DebugSupportNotificationManager(
      application = application,
      appNavigator = appNavigator,
      appStore = appStore
    )
  }

}