package com.yuyakaido.android.blueprint.presentation.view;

import com.yuyakaido.android.blueprint.domain.entity.GithubContributor;

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
