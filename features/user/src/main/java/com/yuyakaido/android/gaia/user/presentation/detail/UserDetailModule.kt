package com.yuyakaido.android.gaia.user.presentation.detail

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class UserDetailModule {

  @Provides
  fun provideUserDetailPage(
    fragment: UserDetailFragment
  ): UserDetailSource {
    return fragment.args.source
  }

}