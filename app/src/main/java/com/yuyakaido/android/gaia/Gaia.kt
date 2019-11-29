package com.yuyakaido.android.gaia

import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.GaiaType
import com.yuyakaido.android.gaia.profile.ProfileFragment
import com.yuyakaido.android.gaia.subreddit.list.SubredditListFragment
import com.yuyakaido.android.gaia.subreddit.list.SubredditListPage

class Gaia : GaiaType() {

  override fun newSubredditListFragment(): Fragment {
    return SubredditListFragment.newInstance(page = SubredditListPage.Popular)
  }

  override fun newProfileFragment(): Fragment {
    return ProfileFragment.newInstance()
  }

}