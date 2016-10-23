package com.yuyakaido.android.blueprint.infra.repository;

import com.yuyakaido.android.blueprint.domain.entity.QiitaItem;
import com.yuyakaido.android.blueprint.domain.repository.QiitaRepository;
import com.yuyakaido.android.blueprint.infra.client.QiitaClient;
import com.yuyakaido.android.blueprint.infra.dao.QiitaDao;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by yuyakaido on 3/12/16.
 */
public class QiitaRepositoryImpl implements QiitaRepository {

    private QiitaClient qiitaClient;
    private QiitaDao qiitaDao;

    @Inject
    public QiitaRepositoryImpl(QiitaClient qiitaClient, QiitaDao qiitaDao) {
        this.qiitaClient = qiitaClient;
        this.qiitaDao = qiitaDao;
    }

    @Override
    public Observable<List<QiitaItem>> getQiitaItems() {
        return CommonRepository.concat(
                qiitaDao.selectQiitaItems(),
                qiitaDao.insertQiitaItems(qiitaClient.getQiitaItems()));
    }

}
