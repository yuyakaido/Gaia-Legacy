package com.yuyakaido.android.genesis.domain.module;

import com.yuyakaido.android.genesis.domain.repository.GithubRepository;
import com.yuyakaido.android.genesis.domain.usecase.GithubUseCase;
import com.yuyakaido.android.genesis.domain.usecase.GithubUseCaseImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuyakaido on 3/6/16.
 */
@Module
public class GithubDomainModule {

    @Provides
    @Singleton
    public GithubUseCase provideGithubUseCase(GithubRepository githubRepository) {
        return new GithubUseCaseImpl(githubRepository);
    }

}
