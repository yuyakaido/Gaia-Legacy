package com.yuyakaido.android.gaia.search

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides

@Module
class SearchModule {

  @Provides
  fun provideSearchViewModel(
    fragment: SearchFragment,
    factory: SearchViewModelFactory
  ): SearchViewModel {
    return ViewModelProviders
      .of(fragment, factory)
      .get(SearchViewModel::class.java)
  }

}