package com.yuyakaido.android.gaia.home.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yuyakaido.android.gaia.core.android.ProfileFragmentResolverType
import com.yuyakaido.android.gaia.core.android.RepoFragmentResolverType

class HomeFragmentPager(
  manager: FragmentManager,
  private val repoFragmentResolver: RepoFragmentResolverType,
  private val profileFragmentResolver: ProfileFragmentResolverType
) : FragmentPagerAdapter(manager) {

  override fun getCount(): Int {
    return Page.values().size
  }

  override fun getItem(position: Int): Fragment {
    return when (Page.fromPosition(position)) {
      Page.Repo -> repoFragmentResolver.getRepoFragment()
      Page.Profile -> profileFragmentResolver.getProfileFragment()
    }
  }

}