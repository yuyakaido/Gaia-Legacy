package com.yuyakaido.android.gaia.core.app

import android.app.Application
import android.content.Intent
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.entity.Article

interface AppRouterType {
  val application: Application

  fun newLaunchAuthorizationActivity(): Intent
  fun newHomeActivity(): Intent
  fun newArticleDetailActivity(article: Article): Intent
  fun newArticleListFragment(): Fragment
  fun newProfileFragment(): Fragment
  fun newSearchFragment(): Fragment
}