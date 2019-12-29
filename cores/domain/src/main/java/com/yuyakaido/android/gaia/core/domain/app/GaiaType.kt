package com.yuyakaido.android.gaia.core.domain.app

import com.facebook.stetho.Stetho
import com.yuyakaido.android.gaia.core.domain.BuildConfig
import dagger.android.support.DaggerApplication
import timber.log.Timber

abstract class GaiaType : DaggerApplication() {

  override fun onCreate() {
    super.onCreate()
    initializeTimber()
    initializeStetho()
  }

  private fun initializeTimber() {
    Timber.plant(Timber.DebugTree())
  }

  private fun initializeStetho() {
    if (BuildConfig.DEBUG) {
      Stetho.initializeWithDefaults(this)
    }
  }

}