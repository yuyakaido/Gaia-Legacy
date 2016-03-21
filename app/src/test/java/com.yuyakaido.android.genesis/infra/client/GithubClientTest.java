package com.yuyakaido.android.genesis.infra.client;

import com.yuyakaido.android.genesis.domain.entity.GithubContributor;
import com.yuyakaido.android.genesis.infra.InfraTest;
import com.yuyakaido.android.genesis.infra.module.GithubInfraTestModule;
import com.yuyakaido.android.genesis.util.ResponseUtil;

import org.junit.Test;

import java.io.File;
import java.util.List;

import okhttp3.mockwebserver.MockWebServer;
import rx.observers.TestSubscriber;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by yuyakaido on 2/15/16.
 */
public class GithubClientTest extends InfraTest {

    @Test
    public void getGithubContributorsTest() throws Exception {
        File file = new File("src/test/assets/json/github_contributors.json");
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.enqueue(ResponseUtil.createMockResponse(file));
        mockWebServer.start();

        GithubInfraTestModule githubInfraTestModule = new GithubInfraTestModule();
        GithubClient githubClient = new GithubClient(
                githubInfraTestModule.provideGithubService(mockWebServer));

        TestSubscriber<List<GithubContributor>> testSubscriber = new TestSubscriber<>();
        githubClient.getGithubContributors().subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        List<GithubContributor> githubContributors = testSubscriber.getOnNextEvents().get(0);
        assertThat(githubContributors.size(), is(1));
    }

}
