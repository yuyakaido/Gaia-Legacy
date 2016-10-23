package com.yuyakaido.android.blueprint.misc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by yuyakaido on 2/10/16.
 */
@RunWith(JUnit4.class)
public class CalculatorTest {

    @Test
    public void sumTest() {
        assertThat(1 + 1, is(2));
    }

}
