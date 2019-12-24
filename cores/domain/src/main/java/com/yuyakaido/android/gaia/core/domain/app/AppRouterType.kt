package com.yuyakaido.android.gaia.core.domain.app

import android.app.Application
import android.content.Intent
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.Me

interface AppRouterType {
  val application: Application

  fun newLaunchAuthorizationActivity(): Intent
  fun newHomeActivity(): Intent
  fun newPopularArticleListFragment(): Fragment
  fun newUpvotedArticleListFragment(me: Me): Fragment
  fun newDownvotedArticleListFragment(me: Me): Fragment
  fun newArticleDetailActivity(article: Article): Intent
  fun newCommunityDetailActivity(community: Community.Summary): Intent
  fun newProfileFragment(): Fragment
  fun newSearchFragment(): Fragment
}