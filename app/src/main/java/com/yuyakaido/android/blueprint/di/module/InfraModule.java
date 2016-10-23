package com.yuyakaido.android.blueprint.di.module;

import android.content.Context;

import com.yuyakaido.android.blueprint.infra.dao.common.OrmaDatabaseWrapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuyakaido on 3/5/16.
 */
@Module(includes = {
        GithubModule.class,
        PixabayModule.class,
        QiitaModule.class})
public class InfraModule {

    @Provides
    @Singleton
    public OrmaDatabaseWrapper provideOrmaDatabaseWrapper(Context context) {
        return new OrmaDatabaseWrapper(context);
    }

}
