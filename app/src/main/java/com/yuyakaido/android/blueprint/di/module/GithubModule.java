package com.yuyakaido.android.blueprint.di.module;

import com.yuyakaido.android.blueprint.domain.repository.GithubRepository;
import com.yuyakaido.android.blueprint.domain.usecase.GithubUseCase;
import com.yuyakaido.android.blueprint.domain.usecase.GithubUseCaseImpl;
import com.yuyakaido.android.blueprint.infra.client.GithubClient;
import com.yuyakaido.android.blueprint.infra.client.common.ApiClientGenerator;
import com.yuyakaido.android.blueprint.infra.dao.GithubDao;
import com.yuyakaido.android.blueprint.infra.dao.common.OrmaDatabaseWrapper;
import com.yuyakaido.android.blueprint.infra.repository.GithubRepositoryImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuyakaido on 3/6/16.
 */
@Module
public class GithubModule {

    @Provides
    public GithubUseCase provideGithubUseCase(GithubRepository githubRepository) {
        return new GithubUseCaseImpl(githubRepository);
    }

    @Provides
    public GithubRepository provideGithubRepository(GithubClient githubClient, GithubDao githubDao) {
        return new GithubRepositoryImpl(githubClient, githubDao);
    }

    @Provides
    public GithubClient provideGithubClient(GithubClient.GithubService githubService) {
        return new GithubClient(githubService);
    }

    @Provides
    public GithubClient.GithubService provideGithubService() {
        return ApiClientGenerator.generate(
                GithubClient.GithubService.class,
                "https://api.github.com");
    }

    @Provides
    public GithubDao provideGithubDao(OrmaDatabaseWrapper ormaDatabaseWrapper) {
        return new GithubDao(ormaDatabaseWrapper.getDatabase());
    }

}
