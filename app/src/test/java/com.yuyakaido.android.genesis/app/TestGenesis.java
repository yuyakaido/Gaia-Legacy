package com.yuyakaido.android.genesis.app;

/**
 * Created by yuyakaido on 3/13/16.
 */
public class TestGenesis extends Genesis {

    @Override
    public void initializeDagger() {
        genesisComponent = DaggerGenesisComponent.builder()
                .genesisModule(new TestGenesisModule(this))
                .build();
    }

    @Override
    protected void initializeTakt() {}

    @Override
    protected void terminateTalk() {}

    @Override
    protected void initializeStetho() {}

}
