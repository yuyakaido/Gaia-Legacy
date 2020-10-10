package com.yuyakaido.android.gaia.core.presentation

import android.app.Application
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User

interface AppNavigatorType {

  val application: Application

  // App
  fun newGatewayActivity(): Intent
  fun newAppActivity(): Intent

  // Support
  fun newSessionListActivity(): Intent

  // Auth
  fun newAuthActivity(state: String): Intent

  // Article
  fun newCommunityDetailArticleListFragment(community: Community.Summary): Fragment
  fun newSubmittedArticleListFragment(user: User): Fragment
  fun newUpvotedArticleListFragment(user: User): Fragment
  fun newDownvotedArticleListFragment(user: User): Fragment
  fun navigateToArticleDetail(controller: NavController, article: Article)

  // Comment
  fun newCommentListFragment(user: User): Fragment
  fun newCommentListFragment(article: Article): Fragment

  // Community
  fun newCommunityFragment(): Fragment
  fun navigateToCommunityDetail(controller: NavController, community: Community.Summary)
  fun newModeratorListFragment(community: Community.Summary): Fragment
  fun newContributorListFragment(community: Community.Summary): Fragment

  // User
  fun navigateToUserDetail(controller: NavController, user: User)
  fun newUserDetailFragmentForMe(): Fragment
  fun newUserDetailFragmentForUser(user: User): Fragment

  // Search
  fun newSearchFragment(): Fragment

}