package com.yuyakaido.android.gaia.user.list

import com.yuyakaido.android.gaia.core.domain.entity.Community
import dagger.Module
import dagger.Provides

@Module
class UserListModule {

  @Provides
  fun provideCommunity(
    fragment: UserListFragment
  ): Community.Summary {
    return fragment.getCommunity()
  }

}