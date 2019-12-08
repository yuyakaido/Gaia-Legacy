package com.yuyakaido.android.gaia.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yuyakaido.android.gaia.core.entity.Me

class VoteListFragmentPagerAdapter(
  manager: FragmentManager,
  private val me: Me
) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

  override fun getPageTitle(position: Int): CharSequence? {
    return VoteListPage.values()[position].name
  }

  override fun getCount(): Int {
    return VoteListPage.values().size
  }

  override fun getItem(position: Int): Fragment {
    return VoteListPage.values()[position].fragment.invoke(me)
  }

}