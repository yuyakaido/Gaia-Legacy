package com.yuyakaido.android.blueprint.domain.repository;

import com.yuyakaido.android.blueprint.domain.entity.PixabayMedia;

import java.util.List;

import rx.Observable;

/**
 * Created by yuyakaido on 3/5/16.
 */
public interface PixabayRepository {

    Observable<List<PixabayMedia>> getPixabayMedias();

}
