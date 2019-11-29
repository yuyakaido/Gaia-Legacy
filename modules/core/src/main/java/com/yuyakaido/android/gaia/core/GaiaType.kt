package com.yuyakaido.android.gaia.core

import android.app.Application
import androidx.fragment.app.Fragment

abstract class GaiaType : Application() {
  abstract fun newSubredditListFragment(): Fragment
  abstract fun newProfileFragment(): Fragment
}