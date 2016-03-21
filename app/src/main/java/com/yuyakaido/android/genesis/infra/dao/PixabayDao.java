package com.yuyakaido.android.genesis.infra.dao;

import com.yuyakaido.android.genesis.domain.entity.OrmaDatabase;
import com.yuyakaido.android.genesis.domain.entity.PixabayMedia;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by yuyakaido on 3/5/16.
 */
public class PixabayDao {

    private OrmaDatabase ormaDatabase;

    public PixabayDao(OrmaDatabase ormaDatabase) {
        this.ormaDatabase = ormaDatabase;
    }

    public Observable<List<PixabayMedia>> selectPixabayMedias() {
        return ormaDatabase.selectFromPixabayMedia()
                .executeAsObservable()
                .toList();
    }

    public Observable<List<PixabayMedia>> insertPixabayMedias(
            Observable<List<PixabayMedia>> observable) {
        return observable.doOnNext(new Action1<List<PixabayMedia>>() {
            @Override
            public void call(List<PixabayMedia> pixabayMedias) {
                ormaDatabase.prepareInsertIntoPixabayMedia()
                        .executeAll(pixabayMedias);
            }
        });
    }

}
