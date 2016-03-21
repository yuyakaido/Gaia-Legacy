package com.yuyakaido.android.genesis.infra.client.common;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by yuyakaido on 3/8/16.
 */
public class CommonClient {

    private static final int RETRY_COUNT = 3;
    private static final int RETRY_DELAY = 3;

    public static <T> Observable<T> retry(Observable<T> observable) {
        return observable.retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                return observable.take(RETRY_COUNT).flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        return Observable.timer(RETRY_DELAY, TimeUnit.SECONDS);
                    }
                });
            }
        });
    }

}
