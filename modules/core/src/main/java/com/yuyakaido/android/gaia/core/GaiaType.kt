package com.yuyakaido.android.gaia.core

import android.app.Application
import android.content.Intent
import androidx.fragment.app.Fragment

abstract class GaiaType : Application() {
  abstract val redditPublicService: RedditPublicService
  abstract val redditAuthService: RedditAuthService

  abstract fun newSubredditDetailActivity(subreddit: Subreddit): Intent
  abstract fun newSubredditListFragment(): Fragment
  abstract fun newProfileFragment(): Fragment
  abstract fun newSearchFragment(): Fragment
}