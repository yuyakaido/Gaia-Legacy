package com.yuyakaido.android.gaia.home

import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.domain.app.AppRouterType

enum class HomePage(
  val id: Int,
  val fragment: (AppRouterType) -> Fragment
  ) {
  Popular(
    id = R.id.navigation_popular,
    fragment = fun (router: AppRouterType) = router.newArticleListFragment()
  ),
  Search(
    id = R.id.navigation_search,
    fragment = fun (router: AppRouterType) = router.newSearchFragment()
  ),
  Profile(
    id = R.id.navigation_profile,
    fragment = fun (router: AppRouterType) = router.newProfileFragment()
  );

  companion object {
    fun from(id: Int): HomePage {
      return values().first { it.id == id }
    }
  }
}
