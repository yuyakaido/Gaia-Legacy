package com.yuyakaido.android.gaia

import android.content.Intent
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.GaiaType
import com.yuyakaido.android.gaia.core.RedditAuthService
import com.yuyakaido.android.gaia.core.RedditPublicService
import com.yuyakaido.android.gaia.core.Subreddit
import com.yuyakaido.android.gaia.profile.ProfileFragment
import com.yuyakaido.android.gaia.search.SearchFragment
import com.yuyakaido.android.gaia.subreddit.detail.SubredditActivity
import com.yuyakaido.android.gaia.subreddit.list.SubredditListFragment
import com.yuyakaido.android.gaia.subreddit.list.SubredditListPage
import timber.log.Timber
import javax.inject.Inject

class Gaia : GaiaType() {

  @Inject
  override lateinit var redditPublicService: RedditPublicService

  @Inject
  override lateinit var redditAuthService: RedditAuthService

  override fun onCreate() {
    super.onCreate()
    initializeDagger()
    initializeTimber()
  }

  override fun newSubredditActivity(subreddit: Subreddit): Intent {
    return SubredditActivity.createIntent(this, subreddit)
  }

  override fun newSubredditListFragment(): Fragment {
    return SubredditListFragment.newInstance(page = SubredditListPage.Popular)
  }

  override fun newProfileFragment(): Fragment {
    return ProfileFragment.newInstance()
  }

  override fun newSearchFragment(): Fragment {
    return SearchFragment.newInstance()
  }

  private fun initializeDagger() {
    DaggerAppComponent
      .builder()
      .build()
      .inject(this)
  }

  private fun initializeTimber() {
    Timber.plant(Timber.DebugTree())
  }

}