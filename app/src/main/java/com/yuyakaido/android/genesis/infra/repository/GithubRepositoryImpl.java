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

    private GithubClient githubClient;
    private GithubDao githubDao;

    @Inject
    public GithubRepositoryImpl(GithubClient githubClient, GithubDao githubDao) {
        this.githubClient = githubClient;
        this.githubDao = githubDao;
    }

    @Override
    public Observable<List<GithubContributor>> getGithubContributors() {
        return CommonRepository.concat(
                githubDao.selectGithubContributors(),
                githubDao.insertGithubContributors(githubClient.getGithubContributors()));
    }

}
