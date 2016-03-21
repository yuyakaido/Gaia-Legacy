package com.yuyakaido.android.genesis.domain.usecase;

import com.yuyakaido.android.genesis.domain.entity.QiitaItem;

import java.util.List;

import rx.Observable;

/**
 * Created by yuyakaido on 3/12/16.
 */
public interface QiitaUseCase {

    Observable<List<QiitaItem>> getQiitaItems();

}
