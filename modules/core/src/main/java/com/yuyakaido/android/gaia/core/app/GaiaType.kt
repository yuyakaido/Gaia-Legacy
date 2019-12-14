package com.yuyakaido.android.gaia.core.app

import com.yuyakaido.android.gaia.core.value.AccessToken
import dagger.android.support.DaggerApplication
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

abstract class GaiaType : DaggerApplication() {

  @Inject
  internal lateinit var appRouter: AppRouterType

  override fun onCreate() {
    super.onCreate()
    initializeTimber()
    initializeExceptionHandler()
  }

  private fun initializeTimber() {
    Timber.plant(Timber.DebugTree())
  }

  private fun initializeExceptionHandler() {
    val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
    Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
      if (throwable is HttpException) {
        AccessToken.delete(this)
        startActivity(appRouter.newLaunchAuthorizationActivity())
      } else {
        defaultHandler?.uncaughtException(thread, throwable)
      }
    }
  }

}