package com.yuyakaido.android.gaia

import android.app.Application
import android.content.Intent
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.article.detail.ArticleDetailActivity
import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import com.yuyakaido.android.gaia.article.list.ArticleListSource
import com.yuyakaido.android.gaia.comment.list.CommentListFragment
import com.yuyakaido.android.gaia.community.detail.CommunityDetailActivity
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.home.HomeActivity
import com.yuyakaido.android.gaia.search.SearchFragment
import com.yuyakaido.android.gaia.user.detail.UserDetailActivity
import com.yuyakaido.android.gaia.user.detail.UserDetailFragment
import com.yuyakaido.android.gaia.user.list.UserListFragment
import com.yuyakaido.android.gaia.user.list.UserListSource
import javax.inject.Inject

class AppRouter @Inject constructor(
  override val application: Application
) : AppRouterType {

  override fun newHomeActivity(): Intent {
    return HomeActivity.createIntent(context = application)
  }

  override fun newPopularArticleListFragment(): Fragment {
    return ArticleListFragment.newInstance(source = ArticleListSource.Popular)
  }

  override fun newCommunityDetailArticleListFragment(community: Community.Summary): Fragment {
    val page = ArticleListSource.CommunityDetail(community = community)
    return ArticleListFragment.newInstance(source = page)
  }

  override fun newSubmittedArticleListFragment(user: User): Fragment {
    val page = ArticleListSource.Submit(user = user)
    return ArticleListFragment.newInstance(source = page)
  }

  override fun newUpvotedArticleListFragment(user: User): Fragment {
    val page = ArticleListSource.Upvote(user = user)
    return ArticleListFragment.newInstance(source = page)
  }

  override fun newDownvotedArticleListFragment(user: User): Fragment {
    val page = ArticleListSource.Downvote(user = user)
    return ArticleListFragment.newInstance(source = page)
  }

  override fun newArticleDetailActivity(article: Article): Intent {
    return ArticleDetailActivity.createIntent(context = application, article = article)
  }

  override fun newCommentListFragment(user: User): Fragment {
    return CommentListFragment.createIntent(user = user)
  }

  override fun newCommentListFragment(article: Article): Fragment {
    return CommentListFragment.createIntent(article = article)
  }

  override fun newCommunityDetailActivity(community: Community.Summary): Intent {
    return CommunityDetailActivity.createIntent(context = application, community = community)
  }

  override fun newModeratorListFragment(community: Community.Summary): Fragment {
    val page = UserListSource.Moderator(community = community)
    return UserListFragment.newInstance(source = page)
  }

  override fun newContributorListFragment(community: Community.Summary): Fragment {
    val page = UserListSource.Contributor(community = community)
    return UserListFragment.newInstance(source = page)
  }

  override fun newUserDetailActivity(user: User): Intent {
    return UserDetailActivity.createIntent(context = application, user = user)
  }

  override fun newUserDetailFragmentForUser(user: User): Fragment {
    return UserDetailFragment.newInstanceForUser(user = user)
  }

  override fun newUserDetailFragmentForMe(): Fragment {
    return UserDetailFragment.newInstanceForMe()
  }

  override fun newSearchFragment(): Fragment {
    return SearchFragment.newInstance()
  }

}