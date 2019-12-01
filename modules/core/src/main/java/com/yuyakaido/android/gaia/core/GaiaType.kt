package com.yuyakaido.android.gaia.core

import android.app.Application
import android.content.Intent
import androidx.fragment.app.Fragment

abstract class GaiaType : Application() {
  abstract val redditService: RedditService

  abstract fun newSubredditActivity(subreddit: Subreddit): Intent
  abstract fun newSubredditListFragment(): Fragment
  abstract fun newProfileFragment(): Fragment
}