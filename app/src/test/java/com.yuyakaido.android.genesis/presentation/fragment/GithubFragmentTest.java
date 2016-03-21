package com.yuyakaido.android.genesis.presentation.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.yuyakaido.android.genesis.R;
import com.yuyakaido.android.genesis.domain.entity.GithubContributor;
import com.yuyakaido.android.genesis.presentation.PresentationTest;
import com.yuyakaido.android.genesis.presentation.activity.MainActivity;
import com.yuyakaido.android.genesis.presentation.activity.WebViewActivity;

import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by yuyakaido on 3/13/16.
 */
public class GithubFragmentTest extends PresentationTest {

    private GithubFragment githubFragment;
    private View view;

    @Override
    public void setUp() {
        super.setUp();
        githubFragment = GithubFragment.newInstance();
        SupportFragmentTestUtil.startVisibleFragment(githubFragment);
        view = githubFragment.getView();
    }

    @Test
    public void setGithubContributorsTest() {
        ListView listView = (ListView) view.findViewById(R.id.fragment_github_list_view);

        assertThat(listView.getCount(), is(0));

        List<GithubContributor> githubContributors = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GithubContributor githubContributor = new GithubContributor();
            githubContributor.login = String.valueOf(i);
            githubContributor.avatarUrl = String.valueOf(i);
            githubContributor.htmlUrl = String.valueOf(i);
            githubContributors.add(githubContributor);
        }
        githubFragment.setGithubContributors(githubContributors);
        assertThat(listView.getCount(), is(10));

        githubFragment.setGithubContributors(new ArrayList<GithubContributor>());
        assertThat(listView.getCount(), is(0));
    }

    @Test
    public void showProgressBarTest() {
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view
                .findViewById(R.id.fragment_github_swipe_refresh_layout);
        swipeRefreshLayout.setRefreshing(false);

        assertThat(swipeRefreshLayout.isRefreshing(), is(false));

        githubFragment.showProgressBar();

        assertThat(swipeRefreshLayout.isRefreshing(), is(true));
    }

    @Test
    public void hideProgressBarTest() {
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view
                .findViewById(R.id.fragment_github_swipe_refresh_layout);
        swipeRefreshLayout.setRefreshing(true);

        assertThat(swipeRefreshLayout.isRefreshing(), is(true));

        githubFragment.hideProgressBar();

        assertThat(swipeRefreshLayout.isRefreshing(), is(false));
    }

    @Test
    public void startWebViewActivityTest() {
        GithubContributor githubContributor = new GithubContributor();
        githubContributor.htmlUrl = "https://github.com/yuyakaido";

        githubFragment.startWebViewActivity(githubContributor);

        ShadowActivity shadowActivity = Shadows.shadowOf(
                Robolectric.buildActivity(MainActivity.class).create().get());
        Intent intent = shadowActivity.peekNextStartedActivity();

        assertNotNull(intent);

        ShadowIntent shadowIntent = Shadows.shadowOf(intent);
        assertThat(shadowIntent.getComponent().getClassName(),
                is(WebViewActivity.class.getName()));
    }

}
