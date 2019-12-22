package com.yuyakaido.android.gaia.core.domain.app

import android.app.Application
import android.content.Intent
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.entity.Me
import com.yuyakaido.android.gaia.core.domain.value.VoteListPage

interface AppRouterType {
  val application: Application

  fun newLaunchAuthorizationActivity(): Intent
  fun newHomeActivity(): Intent
  fun newArticleListFragment(): Fragment
  fun newArticleDetailActivity(article: Article): Intent
  fun newCommunityDetailActivity(community: Community.Summary): Intent
  fun newProfileFragment(): Fragment
  fun newVoteListFragment(me: Me, page: VoteListPage): Fragment
  fun newSearchFragment(): Fragment
}