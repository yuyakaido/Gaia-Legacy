package com.yuyakaido.android.genesis.app;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by yuyakaido on 3/13/16.
 */
@Module
public class TestGenesisModule extends GenesisModule {

    public TestGenesisModule(Application application) {
        super(application);
    }

    @Override
    @Provides
    public Scheduler provideScheduler() {
        return AndroidSchedulers.mainThread();
    }

}
