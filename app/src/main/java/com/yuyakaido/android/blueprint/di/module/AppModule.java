package com.yuyakaido.android.blueprint.di.module;

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
public class AppModule {

    private Application application;

    public AppModule(Application application) {
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
