package com.yuyakaido.android.gaia.user.detail

import com.yuyakaido.android.gaia.core.domain.value.UserDetailSource
import dagger.Module
import dagger.Provides

@Module
class UserDetailModule {

  @Provides
  fun provideUserDetailPage(
    fragment: UserDetailFragment
  ): UserDetailSource {
    return fragment.getUserDetailSource()
  }

}