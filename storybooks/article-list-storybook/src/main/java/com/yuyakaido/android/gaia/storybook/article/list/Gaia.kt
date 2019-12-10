package com.yuyakaido.android.gaia.storybook.article.list

import android.content.Intent
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.app.GaiaType
import com.yuyakaido.android.gaia.core.entity.Article
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class Gaia : GaiaType() {

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerAppComponent
      .builder()
      .application(this)
      .build()
  }

  override fun newArticleDetailActivity(article: Article): Intent {
    TODO("not implemented")
  }

  override fun newArticleListFragment(): Fragment {
    TODO("not implemented")
  }

  override fun newProfileFragment(): Fragment {
    TODO("not implemented")
  }

  override fun newSearchFragment(): Fragment {
    TODO("not implemented")
  }

}