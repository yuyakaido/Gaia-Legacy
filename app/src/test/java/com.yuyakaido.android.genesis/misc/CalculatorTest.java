package com.yuyakaido.android.genesis.misc;

import com.yuyakaido.android.genesis.Calculator;

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
        assertThat(Calculator.sum(1, 1), is(2));
        assertThat(Calculator.sum(0, 0), is(0));
        assertThat(Calculator.sum(-1, -1), is(-2));
    }

}
