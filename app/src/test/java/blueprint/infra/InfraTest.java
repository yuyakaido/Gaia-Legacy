package com.yuyakaido.android.blueprint.infra;

import com.yuyakaido.android.blueprint.app.GenesisTest;
import com.yuyakaido.android.blueprint.domain.entity.OrmaDatabase;

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
