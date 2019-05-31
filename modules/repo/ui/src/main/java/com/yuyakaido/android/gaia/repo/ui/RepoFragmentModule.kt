package com.yuyakaido.android.gaia.repo.ui

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides

@Module
class RepoFragmentModule {

    @Provides
    fun provideRepoViewModel(
        fragment: RepoFragment,
        factory: RepoViewModelFactory
    ): RepoViewModel {
        return ViewModelProviders
            .of(fragment, factory)
            .get(RepoViewModel::class.java)
    }

}