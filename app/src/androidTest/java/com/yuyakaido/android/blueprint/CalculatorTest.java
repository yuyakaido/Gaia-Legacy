package com.yuyakaido.android.blueprint;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by yuyakaido on 2/10/16.
 */
@RunWith(AndroidJUnit4.class)
public class CalculatorTest {

    @Test
    public void sumTest() {
        assertThat(1 + 1, is(2));
    }

}
