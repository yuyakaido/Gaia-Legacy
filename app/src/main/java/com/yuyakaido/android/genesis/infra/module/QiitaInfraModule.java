package com.yuyakaido.android.genesis.infra.module;

import com.yuyakaido.android.genesis.domain.repository.QiitaRepository;
import com.yuyakaido.android.genesis.infra.client.QiitaClient;
import com.yuyakaido.android.genesis.infra.client.common.ApiClientGenerator;
import com.yuyakaido.android.genesis.infra.dao.QiitaDao;
import com.yuyakaido.android.genesis.infra.dao.common.OrmaDatabaseWrapper;
import com.yuyakaido.android.genesis.infra.repository.QiitaRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuyakaido on 3/12/16.
 */
@Module
public class QiitaInfraModule {

    @Provides
    @Singleton
    public QiitaRepository provideQiitaRepository(QiitaClient qiitaClient, QiitaDao qiitaDao) {
        return new QiitaRepositoryImpl(qiitaClient, qiitaDao);
    }

    @Provides
    @Singleton
    public QiitaClient provideQiitaClient(QiitaClient.QiitaService qiitaService) {
        return new QiitaClient(qiitaService);
    }

    @Provides
    @Singleton
    public QiitaClient.QiitaService provideQiitaService() {
        return ApiClientGenerator.generate(
                QiitaClient.QiitaService.class,
                "https://qiita.com");
    }

    @Provides
    @Singleton
    public QiitaDao provideQiitaDao(OrmaDatabaseWrapper ormaDatabaseWrapper) {
        return new QiitaDao(ormaDatabaseWrapper.getDatabase());
    }

}
