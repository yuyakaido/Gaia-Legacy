package com.yuyakaido.android.gaia.user.presentation.detail

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType
import com.yuyakaido.android.gaia.core.domain.entity.User

class UserDetailFragmentPagerAdapter(
  manager: FragmentManager,
  private val context: Context,
  private val page: UserDetailPage,
  private val router: AppRouterType,
  private val user: User.Detail
) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

  override fun getPageTitle(position: Int): CharSequence? {
    return context.getString(page.title(index = position))
  }

  override fun getCount(): Int {
    return page.size()
  }

  override fun getItem(position: Int): Fragment {
    return page.fragment(router = router, user = user, index = position)
  }

}