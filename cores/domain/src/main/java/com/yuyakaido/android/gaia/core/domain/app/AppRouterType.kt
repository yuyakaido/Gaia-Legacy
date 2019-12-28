package com.yuyakaido.android.gaia.core.domain.app

import android.app.Application
import android.content.Intent
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.value.UserDetailPage

interface AppRouterType {

  val application: Application

  // Auth
  fun newLaunchAuthorizationActivity(): Intent

  // Home
  fun newHomeActivity(): Intent

  // Article
  fun newPopularArticleListFragment(): Fragment
  fun newCommunityDetailArticleListFragment(community: Community.Summary): Fragment
  fun newSubmittedArticleListFragment(user: User): Fragment
  fun newUpvotedArticleListFragment(user: User): Fragment
  fun newDownvotedArticleListFragment(user: User): Fragment
  fun newArticleDetailActivity(article: Article): Intent

  // Community
  fun newCommunityDetailActivity(community: Community.Summary): Intent
  fun newModeratorListFragment(community: Community.Summary): Fragment
  fun newContributorListFragment(community: Community.Summary): Fragment

  // User
  fun newUserDetailActivity(user: User): Intent
  fun newUserDetailFragment(page: UserDetailPage): Fragment

  // Search
  fun newSearchFragment(): Fragment

}