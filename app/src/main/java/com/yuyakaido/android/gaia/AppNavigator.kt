package com.yuyakaido.android.gaia

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import com.yuyakaido.android.gaia.article.list.ArticleListFragmentArgs
import com.yuyakaido.android.gaia.article.list.ArticleListFragmentDirections
import com.yuyakaido.android.gaia.article.list.ArticleListSource
import com.yuyakaido.android.gaia.comment.CommentListFragment
import com.yuyakaido.android.gaia.community.detail.CommunityDetailFragmentDirections
import com.yuyakaido.android.gaia.community.list.CommunityListFragment
import com.yuyakaido.android.gaia.core.domain.app.Constant
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.presentation.AppNavigatorType
import com.yuyakaido.android.gaia.search.presentation.SearchFragment
import com.yuyakaido.android.gaia.support.SessionListActivity
import com.yuyakaido.android.gaia.user.presentation.detail.UserDetailFragment
import com.yuyakaido.android.gaia.user.presentation.detail.UserDetailFragmentArgs
import com.yuyakaido.android.gaia.user.presentation.detail.UserDetailSource
import com.yuyakaido.android.gaia.user.presentation.list.UserListFragment
import com.yuyakaido.android.gaia.user.presentation.list.UserListFragmentDirections
import com.yuyakaido.android.gaia.user.presentation.list.UserListSource

class AppNavigator(
  override val application: Application
) : AppNavigatorType {

  override fun newGatewayActivity(): Intent {
    return GatewayActivity.createIntent(application)
  }

  override fun newAppActivity(): Intent {
    return AppActivity.createIntent(application)
  }

  override fun newSessionListActivity(): Intent {
    return SessionListActivity.createIntent(application)
  }

  override fun newAuthActivity(id: String): Intent {
    val uri = Uri.Builder()
      .scheme(Constant.OAUTH_SCHEME)
      .encodedAuthority(Constant.OAUTH_AUTHORITY)
      .encodedPath(Constant.OAUTH_PATH)
      .appendQueryParameter("client_id", Constant.OAUTH_CLIENT_ID)
      .appendQueryParameter("response_type", Constant.OAUTH_RESPONSE_TYPE)
      .appendQueryParameter("state", id)
      .appendQueryParameter("redirect_uri", Constant.OAUTH_REDIRECT_URI)
      .appendQueryParameter("duration", Constant.OAUTH_DURATION)
      .appendQueryParameter("scope", Constant.OAUTH_SCOPES.joinToString(" "))
      .build()
    return Intent(Intent.ACTION_VIEW, uri)
      .apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      }
  }

  override fun newCommunityDetailArticleListFragment(community: Community.Summary): Fragment {
    val source = ArticleListSource.CommunityDetail(community = community)
    val args = ArticleListFragmentArgs(source)
    return ArticleListFragment.newInstance(args)
  }

  override fun newSubmittedArticleListFragment(user: User): Fragment {
    val source = ArticleListSource.Submit(user = user)
    val args = ArticleListFragmentArgs(source)
    return ArticleListFragment.newInstance(args)
  }

  override fun newUpvotedArticleListFragment(user: User): Fragment {
    val source = ArticleListSource.Upvoted(user = user)
    val args = ArticleListFragmentArgs(source)
    return ArticleListFragment.newInstance(args)
  }

  override fun newDownvotedArticleListFragment(user: User): Fragment {
    val source = ArticleListSource.Downvoted(user = user)
    val args = ArticleListFragmentArgs(source)
    return ArticleListFragment.newInstance(args)
  }

  override fun navigateToArticleDetail(
    controller: NavController,
    article: Article
  ) {
    controller.navigate(
      ArticleListFragmentDirections.actionArticleDetail(article)
    )
  }

  override fun newCommentListFragment(user: User): Fragment {
    return CommentListFragment.createIntent(user)
  }

  override fun newCommentListFragment(article: Article): Fragment {
    return CommentListFragment.createIntent(article)
  }

  override fun newCommunityFragment(): Fragment {
    return CommunityListFragment.newInstance()
  }

  override fun navigateToCommunityDetail(
    controller: NavController,
    community: Community.Summary
  ) {
    controller.navigate(
      CommunityDetailFragmentDirections.actionCommunityDetail(community)
    )
  }

  override fun newModeratorListFragment(community: Community.Summary): Fragment {
    val page = UserListSource.Moderator(community)
    return UserListFragment.newInstance(page)
  }

  override fun newContributorListFragment(community: Community.Summary): Fragment {
    val page = UserListSource.Contributor(community)
    return UserListFragment.newInstance(page)
  }

  override fun navigateToUserDetail(
    controller: NavController,
    user: User
  ) {
    controller.navigate(
      UserListFragmentDirections.actionUserDetail(UserDetailSource.Other(user))
    )
  }

  override fun newUserDetailFragmentForUser(user: User): Fragment {
    val source = UserDetailSource.Other(user)
    val args = UserDetailFragmentArgs(source)
    return UserDetailFragment.newInstance(args)
  }

  override fun newUserDetailFragmentForMe(): Fragment {
    val source = UserDetailSource.Me
    val args = UserDetailFragmentArgs(source)
    return UserDetailFragment.newInstance(args)
  }

  override fun newSearchFragment(): Fragment {
    return SearchFragment.newInstance()
  }

}