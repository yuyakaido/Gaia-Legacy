package com.yuyakaido.android.blueprint.domain.repository;

import com.yuyakaido.android.blueprint.domain.entity.QiitaItem;

import java.util.List;

import rx.Observable;

/**
 * Created by yuyakaido on 3/12/16.
 */
public interface QiitaRepository {

    Observable<List<QiitaItem>> getQiitaItems();

}
