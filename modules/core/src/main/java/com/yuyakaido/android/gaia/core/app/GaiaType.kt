package com.yuyakaido.android.gaia.core.app

import android.content.Intent
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.entity.Article
import com.yuyakaido.android.gaia.core.value.AccessToken
import dagger.android.support.DaggerApplication
import retrofit2.HttpException
import timber.log.Timber

abstract class GaiaType : DaggerApplication() {

  abstract fun newLaunchAuthorizationActivity(): Intent
  abstract fun newHomeActivity(): Intent
  abstract fun newArticleDetailActivity(article: Article): Intent
  abstract fun newArticleListFragment(): Fragment
  abstract fun newProfileFragment(): Fragment
  abstract fun newSearchFragment(): Fragment

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
        startActivity(newLaunchAuthorizationActivity())
      } else {
        defaultHandler?.uncaughtException(thread, throwable)
      }
    }
  }

}