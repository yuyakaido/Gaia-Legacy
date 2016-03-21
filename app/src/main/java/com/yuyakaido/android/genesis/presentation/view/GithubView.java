package com.yuyakaido.android.genesis.presentation.view;

import com.yuyakaido.android.genesis.domain.entity.GithubContributor;

import java.util.List;

/**
 * Created by yuyakaido on 3/13/16.
 */
public interface GithubView {

    void initViews();
    void refresh();
    void showProgressBar();
    void hideProgressBar();
    void setGithubContributors(List<GithubContributor> githubContributors);
    void startWebViewActivity(GithubContributor githubContributor);

}
