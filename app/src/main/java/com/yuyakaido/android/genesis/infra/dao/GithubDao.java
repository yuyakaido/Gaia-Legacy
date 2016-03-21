package com.yuyakaido.android.genesis.infra.dao;

import com.yuyakaido.android.genesis.domain.entity.GithubContributor;
import com.yuyakaido.android.genesis.domain.entity.OrmaDatabase;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by yuyakaido on 2/15/16.
 */
public class GithubDao {

    private OrmaDatabase ormaDatabase;

    @Inject
    public GithubDao(OrmaDatabase ormaDatabase) {
        this.ormaDatabase = ormaDatabase;
    }

    public Observable<List<GithubContributor>> selectGithubContributors() {
        return ormaDatabase.selectFromGithubContributor()
                .executeAsObservable()
                .toList();
    }

    public Observable<List<GithubContributor>> insertGithubContributors(
            Observable<List<GithubContributor>> observable) {
        return observable.doOnNext(new Action1<List<GithubContributor>>() {
            @Override
            public void call(List<GithubContributor> githubContributors) {
                ormaDatabase.prepareInsertIntoGithubContributor()
                        .executeAll(githubContributors);
            }
        });
    }

}
