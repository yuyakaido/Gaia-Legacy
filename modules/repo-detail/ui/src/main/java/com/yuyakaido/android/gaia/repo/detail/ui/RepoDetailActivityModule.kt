package com.yuyakaido.android.gaia.repo.detail.ui

import androidx.lifecycle.ViewModelProviders
import com.yuyakaido.android.gaia.core.java.Repo
import dagger.Module
import dagger.Provides

@Module
class RepoDetailActivityModule {

    @Provides
    fun provideRepo(activity: RepoDetailActivity): Repo {
        return activity.getRepo()
    }

    @Provides
    fun provideRepoDetailViewModel(
        activity: RepoDetailActivity,
        factory: RepoDetailViewModelFactory
    ): RepoDetailViewModel {
        return ViewModelProviders
            .of(activity, factory)
            .get(RepoDetailViewModel::class.java)
    }

}