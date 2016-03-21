package com.yuyakaido.android.genesis.domain.usecase;

import com.yuyakaido.android.genesis.domain.entity.PixabayMedia;

import java.util.List;

import rx.Observable;

/**
 * Created by yuyakaido on 3/5/16.
 */
public interface PixabayUseCase {

    Observable<List<PixabayMedia>> getPixabayMedias();

}
