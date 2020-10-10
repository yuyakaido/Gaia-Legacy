package com.yuyakaido.android.gaia.support

import android.app.Application
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.presentation.AppNavigatorType

class ReleaseSupportNotificationManager(
  override val application: Application,
  override val appNavigator: AppNavigatorType,
  override val appStore: AppStore
) : SupportNotificationManager {

  override fun initialize() = Unit

}