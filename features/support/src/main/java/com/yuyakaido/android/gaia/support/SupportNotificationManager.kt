package com.yuyakaido.android.gaia.support

import android.app.Application
import com.yuyakaido.android.gaia.core.AppStore

interface SupportNotificationManager {
  val application: Application
  val appStore: AppStore
  fun initialize()
}