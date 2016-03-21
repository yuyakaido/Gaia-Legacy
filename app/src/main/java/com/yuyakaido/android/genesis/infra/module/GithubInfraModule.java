package com.yuyakaido.android.genesis.infra.module;

import com.yuyakaido.android.genesis.domain.repository.GithubRepository;
import com.yuyakaido.android.genesis.infra.client.GithubClient;
import com.yuyakaido.android.genesis.infra.client.common.ApiClientGenerator;
import com.yuyakaido.android.genesis.infra.dao.GithubDao;
import com.yuyakaido.android.genesis.infra.dao.common.OrmaDatabaseWrapper;
import com.yuyakaido.android.genesis.infra.repository.GithubRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuyakaido on 3/6/16.
 */
@Module
public class GithubInfraModule {

    @Provides
    @Singleton
    public GithubRepository provideGithubRepository(GithubClient githubClient, GithubDao githubDao) {
        return new GithubRepositoryImpl(githubClient, githubDao);
    }

    @Provides
    @Singleton
    public GithubClient provideGithubClient(GithubClient.GithubService githubService) {
        return new GithubClient(githubService);
    }

    @Provides
    @Singleton
    public GithubClient.GithubService provideGithubService() {
        return ApiClientGenerator.generate(
                GithubClient.GithubService.class,
                "https://api.github.com");
    }

    @Provides
    @Singleton
    public GithubDao provideGithubDao(OrmaDatabaseWrapper ormaDatabaseWrapper) {
        return new GithubDao(ormaDatabaseWrapper.getDatabase());
    }

}
