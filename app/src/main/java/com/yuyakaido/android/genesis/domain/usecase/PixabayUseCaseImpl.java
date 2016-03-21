package com.yuyakaido.android.genesis.domain.usecase;

import com.yuyakaido.android.genesis.domain.entity.PixabayMedia;
import com.yuyakaido.android.genesis.domain.repository.PixabayRepository;

import java.util.List;

import rx.Observable;

/**
 * Created by yuyakaido on 3/5/16.
 */
public class PixabayUseCaseImpl implements PixabayUseCase {

    private final PixabayRepository pixabayRepository;

    public PixabayUseCaseImpl(PixabayRepository pixabayRepository) {
        this.pixabayRepository = pixabayRepository;
    }

    @Override
    public Observable<List<PixabayMedia>> getPixabayMedias() {
        return pixabayRepository.getPixabayMedias();
    }

}
