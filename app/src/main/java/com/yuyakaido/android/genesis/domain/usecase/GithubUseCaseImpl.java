package com.yuyakaido.android.genesis.domain.usecase;

import com.yuyakaido.android.genesis.domain.entity.GithubContributor;
import com.yuyakaido.android.genesis.domain.repository.GithubRepository;

import java.util.List;

import rx.Observable;

/**
 * Created by yuyakaido on 3/5/16.
 */
public class GithubUseCaseImpl implements GithubUseCase {

    private final GithubRepository githubRepository;

    public GithubUseCaseImpl(GithubRepository githubRepository) {
        this.githubRepository = githubRepository;
    }

    @Override
    public Observable<List<GithubContributor>> getGithubContributors() {
        return githubRepository.getGithubContributors();
    }

}
