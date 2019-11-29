package com.yuyakaido.android.gaia.home

import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.GaiaType

enum class HomePage(
  val id: Int,
  val fragment: (GaiaType) -> Fragment
  ) {
  Popular(
    id = R.id.navigation_popular,
    fragment = fun (app: GaiaType) = app.newSubredditListFragment()
  ),
  Profile(
    id = R.id.navigation_profile,
    fragment = fun (app: GaiaType) = app.newProfileFragment()
  );

  companion object {
    fun from(id: Int): HomePage {
      return values().first { it.id == id }
    }
  }
}
