package com.yuyakaido.android.gaia.core.app

import android.content.Intent
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.entity.Article
import dagger.android.support.DaggerApplication
import timber.log.Timber

abstract class GaiaType : DaggerApplication() {

  abstract fun newArticleDetailActivity(article: Article): Intent
  abstract fun newArticleListFragment(): Fragment
  abstract fun newProfileFragment(): Fragment
  abstract fun newSearchFragment(): Fragment

  override fun onCreate() {
    super.onCreate()
    initializeTimber()
  }

  private fun initializeTimber() {
    Timber.plant(Timber.DebugTree())
  }

}