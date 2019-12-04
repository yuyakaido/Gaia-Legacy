package com.yuyakaido.android.gaia.profile

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides

@Module
class ProfileModule {

  @Provides
  fun provideProfileViewModel(
    fragment: ProfileFragment,
    factory: ProfileViewModelFactory
  ): ProfileViewModel {
    return ViewModelProviders
      .of(fragment, factory)
      .get(ProfileViewModel::class.java)
  }

}