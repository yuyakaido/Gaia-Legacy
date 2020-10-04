package com.yuyakaido.android.gaia.community.detail

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yuyakaido.android.gaia.core.presentation.AppNavigatorType
import com.yuyakaido.android.gaia.core.domain.entity.Community

class CommunityDetailFragmentPagerAdapter(
  manager: FragmentManager,
  private val context: Context,
  private val navigator: AppNavigatorType,
  private val community: Community.Summary
) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

  override fun getPageTitle(position: Int): CharSequence? {
    return context.getString(CommunityDetailPage.values()[position].title)
  }

  override fun getCount(): Int {
    return CommunityDetailPage.values().size
  }

  override fun getItem(position: Int): Fragment {
    val page = CommunityDetailPage.values()[position]
    return page.fragment.invoke(navigator, community)
  }

}