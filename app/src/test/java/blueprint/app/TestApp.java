package com.yuyakaido.android.blueprint.app;

import com.yuyakaido.android.blueprint.di.component.DaggerAppComponent;

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
