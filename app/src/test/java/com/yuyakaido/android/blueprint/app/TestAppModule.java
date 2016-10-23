package com.yuyakaido.android.blueprint.app;

import android.app.Application;

import com.yuyakaido.android.blueprint.di.module.AppModule;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by yuyakaido on 3/13/16.
 */
@Module
public class TestAppModule extends AppModule {

    public TestAppModule(Application application) {
        super(application);
    }

    @Override
    @Provides
    public Scheduler provideScheduler() {
        return AndroidSchedulers.mainThread();
    }

}
