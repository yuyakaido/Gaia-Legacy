package com.yuyakaido.android.blueprint.di.component;

import com.yuyakaido.android.blueprint.di.module.AppModule;
import com.yuyakaido.android.blueprint.di.module.DomainModule;
import com.yuyakaido.android.blueprint.di.module.GithubModule;
import com.yuyakaido.android.blueprint.di.module.InfraModule;
import com.yuyakaido.android.blueprint.presentation.fragment.GithubFragment;
import com.yuyakaido.android.blueprint.presentation.fragment.PixabayFragment;
import com.yuyakaido.android.blueprint.presentation.fragment.QiitaFragment;
import com.yuyakaido.android.blueprint.presentation.presenter.GithubPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yuyakaido on 2/21/16.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        DomainModule.class,
        InfraModule.class,
        GithubModule.class})
public interface AppComponent {
    void inject(GithubFragment githubFragment);
    void inject(GithubPresenter githubPresenter);
    void inject(PixabayFragment pixabayFragment);
    void inject(QiitaFragment qiitaFragment);
}
