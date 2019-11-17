package com.yuyakaido.android.gaia

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SubredditListFragmentPagerAdapter(
  manager: FragmentManager
) : FragmentPagerAdapter(
  manager,
  BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

  override fun getCount(): Int {
    return HomePage.values().size
  }

  override fun getItem(position: Int): Fragment {
    return SubredditListFragment.newInstance(HomePage.values()[position])
  }

}