package com.yuyakaido.android.genesis.domain.repository;

import com.yuyakaido.android.genesis.domain.entity.QiitaItem;

import java.util.List;

import rx.Observable;

/**
 * Created by yuyakaido on 3/12/16.
 */
public interface QiitaRepository {

    Observable<List<QiitaItem>> getQiitaItems();

}
