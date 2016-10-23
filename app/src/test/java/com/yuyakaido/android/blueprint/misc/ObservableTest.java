package com.yuyakaido.android.blueprint.misc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import rx.Observable;
import rx.observers.TestSubscriber;

/**
 * Created by yuyakaido on 2/15/16.
 */
@RunWith(JUnit4.class)
public class ObservableTest {

    @Test
    public void subscriberTest() {
        Integer[] integers = new Integer[] {1, 2, 3};
        Observable<Integer> observable = Observable.from(integers);

        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        observable.subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(integers.length);
        testSubscriber.assertValues(integers);
        testSubscriber.assertCompleted();
    }

}
