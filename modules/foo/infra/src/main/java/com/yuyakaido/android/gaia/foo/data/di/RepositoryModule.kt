package com.yuyakaido.android.gaia.foo.data.di

import com.yuyakaido.android.gaia.foo.data.GithubClient
import com.yuyakaido.android.gaia.foo.data.RepoRepository
import com.yuyakaido.android.gaia.foo.domain.RepoRepositoryType
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