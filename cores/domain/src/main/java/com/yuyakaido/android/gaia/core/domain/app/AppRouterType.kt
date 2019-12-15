package com.yuyakaido.android.gaia.core.domain.app

import android.app.Application
import android.content.Intent
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Me
import com.yuyakaido.android.gaia.core.domain.value.VoteListPage

interface AppRouterType {
  val application: Application

  fun newLaunchAuthorizationActivity(): Intent
  fun newHomeActivity(): Intent
  fun newArticleDetailActivity(article: Article): Intent
  fun newArticleListFragment(): Fragment
  fun newProfileFragment(): Fragment
  fun newSearchFragment(): Fragment
  fun newVoteListFragment(me: Me, page: VoteListPage): Fragment
}