package com.yuyakaido.android.gaia.home

import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType

enum class HomePage(
  val id: Int,
  val fragment: (AppRouterType) -> Fragment,
  val shouldShowOptionMenu: Boolean
  ) {
  Popular(
    id = R.id.navigation_popular,
    fragment = fun (router: AppRouterType): Fragment {
      return router.newPopularArticleListFragment()
    },
    shouldShowOptionMenu = true
  ),
  Search(
    id = R.id.navigation_search,
    fragment = fun (router: AppRouterType): Fragment {
      return router.newSearchFragment()
    },
    shouldShowOptionMenu = false
  ),
  Community(
    id = R.id.navigation_community,
    fragment = fun (router: AppRouterType): Fragment {
      return router.newCommunityFragment()
    },
    shouldShowOptionMenu = false
  ),
  Profile(
    id = R.id.navigation_profile,
    fragment = fun (router: AppRouterType): Fragment {
      return router.newUserDetailFragmentForMe()
    },
    shouldShowOptionMenu = false
  );

  companion object {
    fun from(id: Int): HomePage {
      return values().first { it.id == id }
    }
  }
}