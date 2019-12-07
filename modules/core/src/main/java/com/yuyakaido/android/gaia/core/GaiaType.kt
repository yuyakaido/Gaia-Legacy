package com.yuyakaido.android.gaia.core

import android.content.Intent
import androidx.fragment.app.Fragment
import dagger.android.support.DaggerApplication

abstract class GaiaType : DaggerApplication() {
  abstract val redditPublicService: RedditPublicService
  abstract val redditAuthService: RedditAuthService

  abstract fun newArticleDetailActivity(article: Article): Intent
  abstract fun newArticleListFragment(): Fragment
  abstract fun newProfileFragment(): Fragment
  abstract fun newSearchFragment(): Fragment
}