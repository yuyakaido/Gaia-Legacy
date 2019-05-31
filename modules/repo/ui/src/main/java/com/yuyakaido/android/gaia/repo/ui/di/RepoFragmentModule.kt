package com.yuyakaido.android.gaia.repo.ui.di

import androidx.lifecycle.ViewModelProviders
import com.yuyakaido.android.gaia.repo.ui.RepoFragment
import com.yuyakaido.android.gaia.repo.ui.RepoViewModel
import com.yuyakaido.android.gaia.repo.ui.RepoViewModelFactory
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