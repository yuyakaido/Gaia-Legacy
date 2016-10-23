package com.yuyakaido.android.blueprint.infra.repository;

import com.yuyakaido.android.blueprint.domain.entity.PixabayMedia;
import com.yuyakaido.android.blueprint.domain.repository.PixabayRepository;
import com.yuyakaido.android.blueprint.infra.client.PixabayClient;
import com.yuyakaido.android.blueprint.infra.dao.PixabayDao;

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
