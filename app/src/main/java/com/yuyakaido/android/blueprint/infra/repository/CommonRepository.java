package com.yuyakaido.android.blueprint.infra.repository;

import rx.Observable;

/**
 * Created by yuyakaido on 2/21/16.
 */
public class CommonRepository {

    public static <T> Observable<T> concat(
            Observable<T> dao, Observable<T> client) {
        return CommonRepository.error(Observable.concat(dao, client));
    }

    public static <T> Observable<T> error(Observable<T> observable) {
        return observable.onErrorResumeNext(Observable.<T>empty());
    }

}
