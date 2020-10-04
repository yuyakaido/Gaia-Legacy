package com.yuyakaido.android.gaia

import android.app.Application
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import com.yuyakaido.android.gaia.article.list.ArticleListFragmentArgs
import com.yuyakaido.android.gaia.article.list.ArticleListFragmentDirections
import com.yuyakaido.android.gaia.article.list.ArticleListSource
import com.yuyakaido.android.gaia.comment.CommentListFragment
import com.yuyakaido.android.gaia.community.detail.CommunityDetailFragmentDirections
import com.yuyakaido.android.gaia.community.list.CommunityListFragment
import com.yuyakaido.android.gaia.core.presentation.AppNavigatorType
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.search.presentation.SearchFragment
import com.yuyakaido.android.gaia.user.presentation.detail.UserDetailFragment
import com.yuyakaido.android.gaia.user.presentation.detail.UserDetailFragmentArgs
import com.yuyakaido.android.gaia.user.presentation.detail.UserDetailSource
import com.yuyakaido.android.gaia.user.presentation.list.UserListFragment
import com.yuyakaido.android.gaia.user.presentation.list.UserListFragmentDirections
import com.yuyakaido.android.gaia.user.presentation.list.UserListSource
import javax.inject.Inject

class AppNavigator @Inject constructor(
  override val application: Application
) : AppNavigatorType {

  override fun newAppActivity(): Intent {
    return AppActivity.createIntent(context = application)
  }

  override fun newCommunityDetailArticleListFragment(community: Community.Summary): Fragment {
    val source = ArticleListSource.CommunityDetail(community = community)
    val args = ArticleListFragmentArgs(source = source)
    return ArticleListFragment.newInstance(args = args)
  }

  override fun newSubmittedArticleListFragment(user: User): Fragment {
    val source = ArticleListSource.Submit(user = user)
    val args = ArticleListFragmentArgs(source = source)
    return ArticleListFragment.newInstance(args = args)
  }

  override fun newUpvotedArticleListFragment(user: User): Fragment {
    val source = ArticleListSource.Upvote(user = user)
    val args = ArticleListFragmentArgs(source = source)
    return ArticleListFragment.newInstance(args = args)
  }

  override fun newDownvotedArticleListFragment(user: User): Fragment {
    val source = ArticleListSource.Downvote(user = user)
    val args = ArticleListFragmentArgs(source = source)
    return ArticleListFragment.newInstance(args = args)
  }

  override fun navigateToArticleDetail(
    controller: NavController,
    article: Article
  ) {
    controller.navigate(
      ArticleListFragmentDirections.actionArticleDetail(
        article = article
      )
    )
  }

  override fun newCommentListFragment(user: User): Fragment {
    return CommentListFragment.createIntent(user = user)
  }

  override fun newCommentListFragment(article: Article): Fragment {
    return CommentListFragment.createIntent(article = article)
  }

  override fun newCommunityFragment(): Fragment {
    return CommunityListFragment.newInstance()
  }

  override fun navigateToCommunityDetail(
    controller: NavController,
    community: Community.Summary
  ) {
    controller.navigate(
      CommunityDetailFragmentDirections.actionCommunityDetail(
        community = community
      )
    )
  }

  override fun newModeratorListFragment(community: Community.Summary): Fragment {
    val page = UserListSource.Moderator(community = community)
    return UserListFragment.newInstance(source = page)
  }

  override fun newContributorListFragment(community: Community.Summary): Fragment {
    val page = UserListSource.Contributor(community = community)
    return UserListFragment.newInstance(source = page)
  }

  override fun navigateToUserDetail(
    controller: NavController,
    user: User
  ) {
    controller.navigate(
      UserListFragmentDirections.actionUserDetail(
        source = UserDetailSource.Other(user = user)
      )
    )
  }

  override fun newUserDetailFragmentForUser(user: User): Fragment {
    val source = UserDetailSource.Other(user = user)
    val args = UserDetailFragmentArgs(source = source)
    return UserDetailFragment.newInstance(args = args)
  }

  override fun newUserDetailFragmentForMe(): Fragment {
    val source = UserDetailSource.Me
    val args = UserDetailFragmentArgs(source = source)
    return UserDetailFragment.newInstance(args = args)
  }

  override fun newSearchFragment(): Fragment {
    return SearchFragment.newInstance()
  }

}