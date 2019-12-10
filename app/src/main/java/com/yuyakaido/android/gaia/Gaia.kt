package com.yuyakaido.android.gaia

import android.content.Intent
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.article.detail.ArticleDetailActivity
import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import com.yuyakaido.android.gaia.article.list.ArticleListPage
import com.yuyakaido.android.gaia.core.app.GaiaType
import com.yuyakaido.android.gaia.core.entity.Article
import com.yuyakaido.android.gaia.profile.ProfileFragment
import com.yuyakaido.android.gaia.search.SearchFragment
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class Gaia : GaiaType() {

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerAppComponent
      .builder()
      .application(this)
      .build()
  }

  override fun onCreate() {
    super.onCreate()
    initializeTimber()
  }

  override fun newArticleDetailActivity(article: Article): Intent {
    return ArticleDetailActivity.createIntent(this, article)
  }

  override fun newArticleListFragment(): Fragment {
    return ArticleListFragment.newInstance(page = ArticleListPage.Popular)
  }

  override fun newProfileFragment(): Fragment {
    return ProfileFragment.newInstance()
  }

  override fun newSearchFragment(): Fragment {
    return SearchFragment.newInstance()
  }

  private fun initializeTimber() {
    Timber.plant(Timber.DebugTree())
  }

}