package com.yuyakaido.android.genesis.infra.repository;

import com.yuyakaido.android.genesis.domain.entity.PixabayMedia;
import com.yuyakaido.android.genesis.domain.repository.PixabayRepository;
import com.yuyakaido.android.genesis.infra.client.PixabayClient;
import com.yuyakaido.android.genesis.infra.dao.PixabayDao;

import java.util.List;

import rx.Observable;

/**
 * Created by yuyakaido on 3/5/16.
 */
public class PixabayRepositoryImpl implements PixabayRepository {

    private PixabayClient pixabayClient;
    private PixabayDao pixabayDao;

    public PixabayRepositoryImpl(PixabayClient pixabayClient, PixabayDao pixabayDao) {
        this.pixabayClient = pixabayClient;
        this.pixabayDao = pixabayDao;
    }

    @Override
    public Observable<List<PixabayMedia>> getPixabayMedias() {
        return CommonRepository.concat(
                pixabayDao.selectPixabayMedias(),
                pixabayDao.insertPixabayMedias(pixabayClient.getPixabayMedias()));
    }

}
