package com.yuyakaido.android.genesis.app;

import android.content.Context;
import android.os.Build;

import com.yuyakaido.android.genesis.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * Created by yuyakaido on 3/9/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,
        sdk = Build.VERSION_CODES.LOLLIPOP,
        application = TestGenesis.class)
public abstract class GenesisTest {

    @Before
    public void setUp() {}

    @After
    public void tearDown() {}

    public Context getContext() {
        return RuntimeEnvironment.application;
    }

}
