package com.yuyakaido.android.genesis.app;

import com.yuyakaido.android.genesis.domain.module.DomainModule;
import com.yuyakaido.android.genesis.infra.module.InfraModule;
import com.yuyakaido.android.genesis.presentation.activity.MainActivity;
import com.yuyakaido.android.genesis.presentation.fragment.GithubFragment;
import com.yuyakaido.android.genesis.presentation.fragment.PixabayFragment;
import com.yuyakaido.android.genesis.presentation.fragment.QiitaFragment;
import com.yuyakaido.android.genesis.presentation.presenter.GithubPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yuyakaido on 2/21/16.
 */
@Singleton
@Component(modules = {
        GenesisModule.class,
        DomainModule.class,
        InfraModule.class})
public interface GenesisComponent {

    void inject(MainActivity activity);

    void inject(GithubFragment githubFragment);
    void inject(PixabayFragment pixabayFragment);
    void inject(QiitaFragment qiitaFragment);

    void inject(GithubPresenter githubPresenter);

}
