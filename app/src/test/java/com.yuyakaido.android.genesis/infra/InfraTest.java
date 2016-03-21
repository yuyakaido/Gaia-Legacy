package com.yuyakaido.android.genesis.infra;

import com.yuyakaido.android.genesis.app.GenesisTest;
import com.yuyakaido.android.genesis.domain.entity.OrmaDatabase;

import org.junit.Before;
import org.robolectric.RuntimeEnvironment;

/**
 * Created by yuyakaido on 3/6/16.
 */
public abstract class InfraTest extends GenesisTest {

    protected OrmaDatabase ormaDatabase;

    @Before
    public void setUp() {
        ormaDatabase = OrmaDatabase
                .builder(RuntimeEnvironment.application)
                .name(null)
                .build();
    }

}
