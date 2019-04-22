package com.yuyakaido.android.gaia.data.di

import com.yuyakaido.android.gaia.domain.RepoRepositoryType
import com.yuyakaido.android.gaia.data.GithubClient
import com.yuyakaido.android.gaia.data.RepoRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRepoRepositoryType(
        client: GithubClient
    ): RepoRepositoryType {
        return RepoRepository(
            client = client
        )
    }

}