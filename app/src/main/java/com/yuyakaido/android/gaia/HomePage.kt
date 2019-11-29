package com.yuyakaido.android.gaia

import androidx.fragment.app.Fragment

enum class HomePage(
  val id: Int,
  val fragment: () -> Fragment
  ) {
  Popular(
    id = R.id.navigation_popular,
    fragment = fun () = SubredditListFragment.newInstance(page = SubredditListPage.Popular)
  ),
  Profile(
    id = R.id.navigation_profile,
    fragment = fun () = ProfileFragment.newInstance()
  );

  companion object {
    fun from(id: Int): HomePage {
      return values().first { it.id == id }
    }
  }
}
