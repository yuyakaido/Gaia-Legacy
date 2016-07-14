package com.yuyakaido.android.genesis.app;

import com.yuyakaido.android.genesis.di.component.DaggerAppComponent;

/**
 * Created by yuyakaido on 3/13/16.
 */
public class TestApp extends App {

    @Override
    public void initializeDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new TestAppModule(this))
                .build();
    }

    @Override
    protected void initializeTakt() {}

    @Override
    protected void terminateTalk() {}

    @Override
    protected void initializeStetho() {}

}
