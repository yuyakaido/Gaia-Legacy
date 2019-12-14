package com.yuyakaido.android.gaia

import android.app.Application
import android.content.Intent
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.article.detail.ArticleDetailActivity
import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import com.yuyakaido.android.gaia.article.list.ArticleListPage
import com.yuyakaido.android.gaia.auth.LaunchAuthorizationActivity
import com.yuyakaido.android.gaia.core.app.AppRouterType
import com.yuyakaido.android.gaia.core.entity.Article
import com.yuyakaido.android.gaia.home.HomeActivity
import com.yuyakaido.android.gaia.profile.ProfileFragment
import com.yuyakaido.android.gaia.search.SearchFragment
import javax.inject.Inject

class AppRouter @Inject constructor(
  override val application: Application
) : AppRouterType {

  override fun newLaunchAuthorizationActivity(): Intent {
    return LaunchAuthorizationActivity.createIntent(application)
  }

  override fun newHomeActivity(): Intent {
    return HomeActivity.createIntent(application)
  }

  override fun newArticleDetailActivity(article: Article): Intent {
    return ArticleDetailActivity.createIntent(application, article)
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

}