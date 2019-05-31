package com.yuyakaido.android.gaia.repo.ui.di

import androidx.lifecycle.ViewModelProviders
import com.yuyakaido.android.gaia.repo.ui.RepoActivity
import com.yuyakaido.android.gaia.repo.ui.RepoViewModel
import com.yuyakaido.android.gaia.repo.ui.RepoViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class RepoActivityModule {

    @Provides
    fun provideRepoViewModel(
        activity: RepoActivity,
        factory: RepoViewModelFactory
    ): RepoViewModel {
        return ViewModelProviders
            .of(activity, factory)
            .get(RepoViewModel::class.java)
    }

}