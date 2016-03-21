package com.yuyakaido.android.genesis.infra.module;

import com.yuyakaido.android.genesis.infra.client.GithubClient;
import com.yuyakaido.android.genesis.infra.client.common.ApiClientGenerator;

import dagger.Module;
import dagger.Provides;
import okhttp3.mockwebserver.MockWebServer;

/**
 * Created by yuyakaido on 2/22/16.
 */
@Module
public class GithubInfraTestModule {

    @Provides
    public GithubClient.GithubService provideGithubService(MockWebServer mockWebServer) {
        return ApiClientGenerator.generate(
                GithubClient.GithubService.class,
                mockWebServer.url("").toString());
    }

}
