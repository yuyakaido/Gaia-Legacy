package com.yuyakaido.android.genesis.infra.dao;

import com.yuyakaido.android.genesis.domain.entity.OrmaDatabase;
import com.yuyakaido.android.genesis.domain.entity.QiitaItem;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by yuyakaido on 3/12/16.
 */
public class QiitaDao {

    private OrmaDatabase ormaDatabase;

    @Inject
    public QiitaDao(OrmaDatabase ormaDatabase) {
        this.ormaDatabase = ormaDatabase;
    }

    public Observable<List<QiitaItem>> selectQiitaItems() {
        return ormaDatabase.selectFromQiitaItem()
                .executeAsObservable()
                .toList();
    }

    public Observable<List<QiitaItem>> insertQiitaItems(Observable<List<QiitaItem>> observable) {
        return observable.doOnNext(new Action1<List<QiitaItem>>() {
            @Override
            public void call(List<QiitaItem> qiitaItems) {
                ormaDatabase.prepareInsertIntoQiitaItem()
                        .executeAll(qiitaItems);
            }
        });
    }

}
