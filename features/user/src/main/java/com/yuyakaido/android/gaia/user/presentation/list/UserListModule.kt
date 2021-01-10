package com.yuyakaido.android.gaia.user.presentation.list

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class UserListModule {

  @Provides
  fun provideUserListPage(
    fragment: UserListFragment
  ): UserListSource {
    return fragment.getUserListSource()
  }

}