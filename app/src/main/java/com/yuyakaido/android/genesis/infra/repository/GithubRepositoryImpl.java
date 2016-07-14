package com.yuyakaido.android.genesis.infra.repository;

import com.yuyakaido.android.genesis.domain.entity.GithubContributor;
import com.yuyakaido.android.genesis.domain.repository.GithubRepository;
import com.yuyakaido.android.genesis.infra.dao.GithubDao;
import com.yuyakaido.android.genesis.infra.client.GithubClient;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by yuyakaido on 2/15/16.
 */
public class GithubRepositoryImpl implements GithubRepository {

    private GithubClient client;
    private GithubDao dao;

    @Inject
    public GithubRepositoryImpl(GithubClient client, GithubDao dao) {
        this.client = client;
        this.dao = dao;
    }

    @Override
    public Observable<List<GithubContributor>> getGithubContributors() {
        return CommonRepository.concat(
                dao.selectGithubContributors(),
                dao.insertGithubContributors(client.getGithubContributors()));
    }

}
