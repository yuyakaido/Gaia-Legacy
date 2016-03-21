package com.yuyakaido.android.genesis.domain.module;

import dagger.Module;

/**
 * Created by yuyakaido on 3/5/16.
 */
@Module(includes = {
        GithubDomainModule.class,
        PixabayDomainModule.class,
        QiitaDomainModule.class})
public class DomainModule {}
