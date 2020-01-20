package com.yuyakaido.android.storybook

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import com.yuyakaido.android.gaia.article.list.ArticleListSource

class StorybookRouter(override val application: Application) : StorybookRouterType {

  override fun newInstance(): Fragment {
    return ArticleListFragment.newInstance(source = ArticleListSource.Popular)
  }

  override fun createIntent(context: Context): Intent {
    throw UnsupportedOperationException()
  }
}
