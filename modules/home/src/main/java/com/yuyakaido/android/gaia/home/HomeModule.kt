package com.yuyakaido.android.gaia.home

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

  @Provides
  fun provideHomeViewModel(
    activity: HomeActivity,
    factory: HomeViewModelFactory
  ): HomeViewModel {
    return ViewModelProviders
      .of(activity, factory)
      .get(HomeViewModel::class.java)
  }

}