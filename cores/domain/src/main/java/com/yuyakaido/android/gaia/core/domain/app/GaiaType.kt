package com.yuyakaido.android.gaia.core.domain.app

import com.facebook.stetho.Stetho
import com.yuyakaido.android.gaia.core.domain.BuildConfig
import dagger.android.support.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

abstract class GaiaType : DaggerApplication() {

  @Inject
  internal lateinit var appRouter: AppRouterType

  @Inject
  internal lateinit var authTokenService: AuthTokenServiceType

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