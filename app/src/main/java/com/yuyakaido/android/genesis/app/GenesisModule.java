package com.yuyakaido.android.genesis.app;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by yuyakaido on 3/9/16.
 */
@Module
public class GenesisModule {

    private Application application;

    public GenesisModule(Application application) {
        this.application = application;
    }

    @Provides
    public Context provideContext() {
        return application.getApplicationContext();
    }

    @Provides
    public Scheduler provideScheduler() {
        return Schedulers.newThread();
    }

}
