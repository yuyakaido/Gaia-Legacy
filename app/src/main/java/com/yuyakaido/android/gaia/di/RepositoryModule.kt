package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.auth.domain.AuthRepositoryType
import com.yuyakaido.android.gaia.auth.infra.AuthClient
import com.yuyakaido.android.gaia.auth.infra.AuthRepository
import com.yuyakaido.android.gaia.repo.infra.GithubClient
import com.yuyakaido.android.gaia.repo.infra.RepoRepository
import com.yuyakaido.android.gaia.repo.domain.RepoRepositoryType
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

    @Provides
    fun provideAuthRepositoryType(
        client: AuthClient
    ): AuthRepositoryType {
        return AuthRepository(
            client = client
        )
    }

}