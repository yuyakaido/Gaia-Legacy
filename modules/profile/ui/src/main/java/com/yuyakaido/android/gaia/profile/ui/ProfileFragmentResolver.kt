package com.yuyakaido.android.gaia.profile.ui

import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.core.android.ProfileFragmentResolverType

class ProfileFragmentResolver : ProfileFragmentResolverType {

  override fun getProfileFragment(): Fragment {
    return ProfileFragment.newInstance()
  }

}