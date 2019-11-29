package com.yuyakaido.android.gaia

import android.content.Intent
import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.GaiaType
import com.yuyakaido.android.gaia.core.Subreddit
import com.yuyakaido.android.gaia.profile.ProfileFragment
import com.yuyakaido.android.gaia.subreddit.detail.SubredditActivity
import com.yuyakaido.android.gaia.subreddit.list.SubredditListFragment
import com.yuyakaido.android.gaia.subreddit.list.SubredditListPage

class Gaia : GaiaType() {

  override fun newSubredditActivity(subreddit: Subreddit): Intent {
    return SubredditActivity.createIntent(this, subreddit)
  }

  override fun newSubredditListFragment(): Fragment {
    return SubredditListFragment.newInstance(page = SubredditListPage.Popular)
  }

  override fun newProfileFragment(): Fragment {
    return ProfileFragment.newInstance()
  }

}