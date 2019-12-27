package com.yuyakaido.android.gaia

import android.app.Application
import android.content.Intent
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.article.detail.ArticleDetailActivity
import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import com.yuyakaido.android.gaia.auth.LaunchAuthorizationActivity
import com.yuyakaido.android.gaia.community.detail.CommunityDetailActivity
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.Me
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.value.ArticleListPage
import com.yuyakaido.android.gaia.core.domain.value.UserListPage
import com.yuyakaido.android.gaia.home.HomeActivity
import com.yuyakaido.android.gaia.profile.ProfileFragment
import com.yuyakaido.android.gaia.search.SearchFragment
import com.yuyakaido.android.gaia.user.detail.UserDetailActivity
import com.yuyakaido.android.gaia.user.list.UserListFragment
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

  override fun newPopularArticleListFragment(): Fragment {
    return ArticleListFragment.newInstance(page = ArticleListPage.Popular)
  }

  override fun newCommunityDetailArticleListFragment(community: Community.Summary): Fragment {
    val page = ArticleListPage.CommunityDetail(community = community)
    return ArticleListFragment.newInstance(page = page)
  }

  override fun newUpvotedArticleListFragment(me: Me): Fragment {
    return ArticleListFragment.newInstance(page = ArticleListPage.Upvote(me = me))
  }

  override fun newDownvotedArticleListFragment(me: Me): Fragment {
    return ArticleListFragment.newInstance(page = ArticleListPage.Downvote(me = me))
  }

  override fun newArticleDetailActivity(article: Article): Intent {
    return ArticleDetailActivity.createIntent(context = application, article = article)
  }

  override fun newCommunityDetailActivity(community: Community.Summary): Intent {
    return CommunityDetailActivity.createIntent(context = application, community = community)
  }

  override fun newModeratorListFragment(community: Community.Summary): Fragment {
    val page = UserListPage.Moderator(community = community)
    return UserListFragment.newInstance(page = page)
  }

  override fun newContributorListFragment(community: Community.Summary): Fragment {
    val page = UserListPage.Contributor(community = community)
    return UserListFragment.newInstance(page = page)
  }

  override fun newUserDetailActivity(user: User): Intent {
    return UserDetailActivity.createIntent(context = application, user = user)
  }

  override fun newProfileFragment(): Fragment {
    return ProfileFragment.newInstance()
  }

  override fun newSearchFragment(): Fragment {
    return SearchFragment.newInstance()
  }

}