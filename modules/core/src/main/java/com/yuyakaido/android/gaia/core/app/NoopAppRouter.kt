package com.yuyakaido.android.gaia.core.app

import android.app.Application
import android.content.Intent
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.entity.Article
import javax.inject.Inject

class NoopAppRouter @Inject constructor(
  override val application: Application
) : AppRouterType {

  override fun newLaunchAuthorizationActivity(): Intent {
    throw UnsupportedOperationException()
  }

  override fun newHomeActivity(): Intent {
    throw UnsupportedOperationException()
  }

  override fun newArticleDetailActivity(article: Article): Intent {
    throw UnsupportedOperationException()
  }

  override fun newArticleListFragment(): Fragment {
    throw UnsupportedOperationException()
  }

  override fun newProfileFragment(): Fragment {
    throw UnsupportedOperationException()
  }

  override fun newSearchFragment(): Fragment {
    throw UnsupportedOperationException()
  }

}