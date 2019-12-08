package com.yuyakaido.android.gaia.profile

import com.yuyakaido.android.gaia.core.Me
import dagger.Module
import dagger.Provides

@Module
class VoteListModule {

  @Provides
  fun provideMe(
    fragment: VoteListFragment
  ): Me {
    return fragment.getMe()
  }

  @Provides
  fun provideVoteListPage(
    fragment: VoteListFragment
  ): VoteListPage {
    return fragment.getVoteListPage()
  }

}