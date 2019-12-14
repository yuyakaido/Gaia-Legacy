package com.yuyakaido.android.gaia

import android.app.Application
import android.content.Intent
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.article.detail.ArticleDetailActivity
import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import com.yuyakaido.android.gaia.auth.LaunchAuthorizationActivity
import com.yuyakaido.android.gaia.core.app.AppRouterType
import com.yuyakaido.android.gaia.core.entity.Article
import com.yuyakaido.android.gaia.core.entity.Me
import com.yuyakaido.android.gaia.core.value.ArticleListPage
import com.yuyakaido.android.gaia.core.value.VoteListPage
import com.yuyakaido.android.gaia.home.HomeActivity
import com.yuyakaido.android.gaia.profile.ProfileFragment
import com.yuyakaido.android.gaia.profile.VoteListFragment
import com.yuyakaido.android.gaia.search.SearchFragment
import javax.inject.Inject

class AppRouter @Inject constructor(
  override val application: Application
) : AppRouterType {

  override fun newLaunchAuthorizationActivity(): Intent {
    return LaunchAuthorizationActivity.createIntent(context = application)
  }

  override fun newHomeActivity(): Intent {
    return HomeActivity.createIntent(context = application)
  }

  override fun newArticleDetailActivity(article: Article): Intent {
    return ArticleDetailActivity.createIntent(context = application, article = article)
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

  override fun newVoteListFragment(me: Me, page: VoteListPage): Fragment {
    return VoteListFragment.newInstance(me = me, page = page)
  }

}