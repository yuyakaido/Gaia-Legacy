package com.yuyakaido.android.gaia.user.presentation.detail

import dagger.Module
import dagger.Provides

@Module
class UserDetailModule {

  @Provides
  fun provideUserDetailPage(
    fragment: UserDetailFragment
  ): UserDetailSource {
    return fragment.args.source
  }

}