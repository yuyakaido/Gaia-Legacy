package com.yuyakaido.android.gaia.subreddit.list

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides

@Module
class SubredditListModule {

  @Provides
  fun provideSubredditListViewModel(
    fragment: SubredditListFragment,
    factory: SubredditListViewModelFactory
  ): SubredditListViewModel {
    return ViewModelProviders
      .of(fragment, factory)
      .get(SubredditListViewModel::class.java)
  }

}