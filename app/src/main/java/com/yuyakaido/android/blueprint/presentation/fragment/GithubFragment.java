package com.yuyakaido.android.blueprint.presentation.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yuyakaido.android.blueprint.R;
import com.yuyakaido.android.blueprint.app.App;
import com.yuyakaido.android.blueprint.domain.entity.GithubContributor;
import com.yuyakaido.android.blueprint.domain.usecase.GithubUseCase;
import com.yuyakaido.android.blueprint.presentation.activity.WebViewActivity;
import com.yuyakaido.android.blueprint.presentation.adapter.GithubAdapter;
import com.yuyakaido.android.blueprint.presentation.presenter.GithubPresenter;
import com.yuyakaido.android.blueprint.presentation.view.GithubView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yuyakaido on 3/5/16.
 */
public class GithubFragment extends BaseFragment
        implements GithubView,
                   AdapterView.OnItemClickListener,
                   SwipeRefreshLayout.OnRefreshListener {

    @Inject
    GithubUseCase githubUseCase;

    private GithubPresenter githubPresenter;
    private GithubAdapter githubAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    public static GithubFragment newInstance() {
        return new GithubFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_github, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        App.getAppComponent(this).inject(this);
        githubPresenter = new GithubPresenter(getContext(), this, githubUseCase);
        githubPresenter.onCreate();
    }

    @Override
    public void initViews() {
        View view = getView();

        githubAdapter = new GithubAdapter(getContext());
        ListView listView = (ListView) view.findViewById(R.id.fragment_github_list_view);
        listView.setAdapter(githubAdapter);
        listView.setOnItemClickListener(this);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_github_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void refresh() {
        githubPresenter.refresh();
    }

    @Override
    public void showProgressBar() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressBar() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setGithubContributors(List<GithubContributor> githubContributors) {
        githubAdapter.setGithubContributors(githubContributors);
    }

    @Override
    public void startWebViewActivity(GithubContributor githubContributor) {
        startActivity(WebViewActivity.createIntent(getContext(), githubContributor.htmlUrl));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        githubPresenter.onItemClick(githubAdapter.getItem(position));
    }

    @Override
    public void onRefresh() {
        githubPresenter.refresh();
    }

}
