package com.yuyakaido.android.gaia

import androidx.navigation.fragment.NavHostFragment
import com.yuyakaido.android.gaia.article.list.OptionMenuType

class AppNavHostFragment : NavHostFragment(), OptionMenuType {

  override fun shouldShowOptionMenu(): Boolean {
    return true
  }

}