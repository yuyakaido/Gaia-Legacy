package com.yuyakaido.android.gaia.user.detail

import com.yuyakaido.android.gaia.core.domain.value.UserDetailPage
import dagger.Module
import dagger.Provides

@Module
class UserDetailModule {

  @Provides
  fun provideUserDetailPage(
    fragment: UserDetailFragment
  ): UserDetailPage {
    return fragment.getPage()
  }

}