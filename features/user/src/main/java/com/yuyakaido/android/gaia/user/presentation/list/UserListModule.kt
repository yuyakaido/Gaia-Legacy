package com.yuyakaido.android.gaia.user.presentation.list

import dagger.Module
import dagger.Provides

@Module
class UserListModule {

  @Provides
  fun provideUserListPage(
    fragment: UserListFragment
  ): UserListSource {
    return fragment.getUserListSource()
  }

}