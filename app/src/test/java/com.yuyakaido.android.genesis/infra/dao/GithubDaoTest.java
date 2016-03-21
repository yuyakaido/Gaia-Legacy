package com.yuyakaido.android.genesis.infra.dao;

import com.yuyakaido.android.genesis.domain.entity.GithubContributor;
import com.yuyakaido.android.genesis.infra.InfraTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by yuyakaido on 2/16/16.
 */
public class GithubDaoTest extends InfraTest {

    private GithubDao githubDao;

    @Override
    public void setUp() {
        super.setUp();
        githubDao = new GithubDao(ormaDatabase);
    }

    @Test
    public void selectGithubContributorsTest() throws Exception {
        final int COUNT = 10;
        final List<GithubContributor> expectGithubContributors = new ArrayList<>(COUNT);
        for (int i = 0; i < COUNT; i++) {
            GithubContributor githubContributor = new GithubContributor();
            githubContributor.login = String.valueOf(i);
            githubContributor.avatarUrl = String.valueOf(i);
            githubContributor.htmlUrl = String.valueOf(i);
            expectGithubContributors.add(githubContributor);
        }

        Observable<List<GithubContributor>> observable = Observable.create(
                new Observable.OnSubscribe<List<GithubContributor>>() {
                    @Override
                    public void call(Subscriber<? super List<GithubContributor>> subscriber) {
                        subscriber.onNext(expectGithubContributors);
                        subscriber.onCompleted();
                    }
                });
        githubDao.insertGithubContributors(observable).subscribe();

        TestSubscriber<List<GithubContributor>> testSubscriber = new TestSubscriber<>();
        githubDao.selectGithubContributors().subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();

        List<GithubContributor> actualGithubContributors = testSubscriber.getOnNextEvents().get(0);
        assertThat(actualGithubContributors.size(), is(expectGithubContributors.size()));
        for (int i = 0, size = actualGithubContributors.size(); i < size; i++) {
            GithubContributor actualGithubContributor = actualGithubContributors.get(i);
            GithubContributor expectGithubContributor = expectGithubContributors.get(i);

            assertThat(actualGithubContributor.login, is(expectGithubContributor.login));
            assertThat(actualGithubContributor.avatarUrl, is(expectGithubContributor.avatarUrl));
            assertThat(actualGithubContributor.htmlUrl, is(expectGithubContributor.htmlUrl));
        }
    }

    @Test
    public void selectEmptyGithubContributors() throws Exception {
        TestSubscriber<List<GithubContributor>> testSubscriber = new TestSubscriber<>();
        githubDao.selectGithubContributors().subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();

        List<GithubContributor> actualGithubContributors = testSubscriber.getOnNextEvents().get(0);
        assertThat(actualGithubContributors.size(), is(0));
    }

}
