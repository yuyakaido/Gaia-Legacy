package com.yuyakaido.android.gaia.core.app

import android.content.Intent
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.entity.Article
import com.yuyakaido.android.gaia.core.infrastructure.RedditAuthService
import com.yuyakaido.android.gaia.core.infrastructure.RedditPublicService
import dagger.android.support.DaggerApplication

abstract class GaiaType : DaggerApplication() {
  abstract val redditPublicService: RedditPublicService
  abstract val redditAuthService: RedditAuthService

  abstract fun newArticleDetailActivity(article: Article): Intent
  abstract fun newArticleListFragment(): Fragment
  abstract fun newProfileFragment(): Fragment
  abstract fun newSearchFragment(): Fragment
}