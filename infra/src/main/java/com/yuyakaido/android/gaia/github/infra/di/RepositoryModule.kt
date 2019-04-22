package com.yuyakaido.android.gaia.github.infra.di

import com.yuyakaido.android.gaia.domain.RepoRepositoryType
import com.yuyakaido.android.gaia.github.infra.GithubClient
import com.yuyakaido.android.gaia.github.infra.RepoRepository
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