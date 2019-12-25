package com.yuyakaido.android.gaia.community.detail

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.entity.Community

class CommunityDetailFragmentPagerAdapter(
  manager: FragmentManager,
  private val context: Context,
  private val appRouter: AppRouterType,
  private val community: Community.Summary
) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

  override fun getPageTitle(position: Int): CharSequence? {
    return context.getString(R.string.community_articles)
  }

  override fun getCount(): Int {
    return 1
  }

  override fun getItem(position: Int): Fragment {
    return appRouter.newCommunityDetailArticleListFragment(community = community)
  }

}