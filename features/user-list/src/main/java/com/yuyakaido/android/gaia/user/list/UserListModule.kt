package com.yuyakaido.android.gaia.user.list

import com.yuyakaido.android.gaia.core.domain.value.UserListPage
import dagger.Module
import dagger.Provides

@Module
class UserListModule {

  @Provides
  fun provideUserListPage(
    fragment: UserListFragment
  ): UserListPage {
    return fragment.getUserListPage()
  }

}