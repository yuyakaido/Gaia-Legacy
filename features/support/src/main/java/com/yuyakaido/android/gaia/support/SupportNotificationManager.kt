package com.yuyakaido.android.gaia.support

import android.app.Application
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.presentation.AppNavigatorType

interface SupportNotificationManager {
  val application: Application
  val appNavigator: AppNavigatorType
  val appStore: AppStore
  fun initialize()
}