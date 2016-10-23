package com.yuyakaido.android.blueprint.domain.repository;

import com.yuyakaido.android.blueprint.domain.entity.GithubContributor;

import java.util.List;

import rx.Observable;

/**
 * Created by yuyakaido on 3/5/16.
 */
public interface GithubRepository {

    Observable<List<GithubContributor>> getGithubContributors();

}
