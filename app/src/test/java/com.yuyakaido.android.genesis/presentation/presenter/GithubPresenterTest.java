package com.yuyakaido.android.genesis.presentation.presenter;

import com.yuyakaido.android.genesis.domain.entity.GithubContributor;
import com.yuyakaido.android.genesis.domain.usecase.GithubUseCase;
import com.yuyakaido.android.genesis.presentation.PresentationTest;
import com.yuyakaido.android.genesis.presentation.view.GithubView;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by yuyakaido on 3/13/16.
 */
public class GithubPresenterTest extends PresentationTest {

    private GithubView githubView;
    private GithubUseCase githubUseCase;
    private GithubPresenter githubPresenter;

    @Override
    public void setUp() {
        super.setUp();
        githubView = mock(GithubView.class);
        githubUseCase = mock(GithubUseCase.class);
        githubPresenter = new GithubPresenter(getContext(), githubView, githubUseCase);
    }

    @Test
    public void onCreateTest() {
        githubPresenter.onCreate();

        verify(githubView, times(1)).initViews();
        verify(githubView, times(1)).showProgressBar();
        verify(githubView, times(1)).refresh();
    }

    @Test
    public void refreshTest() {
        // ダミーデータを設定
        Observable<List<GithubContributor>> observable = Observable.create(
                new Observable.OnSubscribe<List<GithubContributor>>() {
                    @Override
                    public void call(Subscriber<? super List<GithubContributor>> subscriber) {
                        subscriber.onNext(new ArrayList<GithubContributor>());
                        subscriber.onCompleted();
                    }
                }
        );
        when(githubUseCase.getGithubContributors()).thenReturn(observable);

        githubPresenter.refresh();

        // ダミーデータが受け渡されていることを確認
        verify(githubView, times(1)).setGithubContributors(new ArrayList<GithubContributor>());
        // プログレスバーが非表示になっていることを確認
        verify(githubView, times(1)).hideProgressBar();
    }

    @Test
    public void onItemClickTest() {
        githubPresenter.onItemClick(null);

        verify(githubView, times(1)).startWebViewActivity(null);
    }

}
