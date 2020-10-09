package com.yuyakaido.android.gaia

import android.app.Application
import com.yuyakaido.android.gaia.core.AppStore

class ReleaseSupportNotificationManager(
  override val application: Application,
  override val appStore: AppStore
) : SupportNotificationManager {

  override fun initialize() = Unit

}