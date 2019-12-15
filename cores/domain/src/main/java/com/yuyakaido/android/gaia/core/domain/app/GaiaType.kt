package com.yuyakaido.android.gaia.core.domain.app

import com.facebook.stetho.Stetho
import com.yuyakaido.android.gaia.core.domain.BuildConfig
import dagger.android.support.DaggerApplication
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

abstract class GaiaType : DaggerApplication() {

  @Inject
  internal lateinit var appRouter: AppRouterType

  @Inject
  internal lateinit var accessTokenService: AccessTokenServiceType

  override fun onCreate() {
    super.onCreate()
    initializeTimber()
    initializeStetho()
    initializeExceptionHandler()
  }

  private fun initializeTimber() {
    Timber.plant(Timber.DebugTree())
  }

  private fun initializeStetho() {
    if (BuildConfig.DEBUG) {
      Stetho.initializeWithDefaults(this)
    }
  }

  private fun initializeExceptionHandler() {
    val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
    Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
      if (throwable is HttpException) {
        accessTokenService.delete()
        startActivity(appRouter.newLaunchAuthorizationActivity())
      } else {
        defaultHandler?.uncaughtException(thread, throwable)
      }
    }
  }

}