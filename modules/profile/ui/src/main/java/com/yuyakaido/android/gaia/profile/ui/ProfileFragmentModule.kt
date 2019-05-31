package com.yuyakaido.android.gaia.profile.ui

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides

@Module
class ProfileFragmentModule {

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