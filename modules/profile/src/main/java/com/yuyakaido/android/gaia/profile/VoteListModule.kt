package com.yuyakaido.android.gaia.profile

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides

@Module
class VoteListModule {

  @Provides
  fun provideVoteListViewModel(
    fragment: VoteListFragment,
    factory: VoteListViewModelFactory
  ): VoteListViewModel {
    return ViewModelProviders
      .of(fragment, factory)
      .get(VoteListViewModel::class.java)
  }

}