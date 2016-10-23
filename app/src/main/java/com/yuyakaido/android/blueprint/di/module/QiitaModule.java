package com.yuyakaido.android.blueprint.di.module;

import com.yuyakaido.android.blueprint.domain.repository.QiitaRepository;
import com.yuyakaido.android.blueprint.domain.usecase.QiitaUseCase;
import com.yuyakaido.android.blueprint.domain.usecase.QiitaUseCaseImpl;
import com.yuyakaido.android.blueprint.infra.client.QiitaClient;
import com.yuyakaido.android.blueprint.infra.client.common.ApiClientGenerator;
import com.yuyakaido.android.blueprint.infra.dao.QiitaDao;
import com.yuyakaido.android.blueprint.infra.dao.common.OrmaDatabaseWrapper;
import com.yuyakaido.android.blueprint.infra.repository.QiitaRepositoryImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuyakaido on 3/12/16.
 */
@Module
public class QiitaModule {

    @Provides
    public QiitaUseCase provideQiitaUseCase(QiitaRepository qiitaRepository) {
        return new QiitaUseCaseImpl(qiitaRepository);
    }

    @Provides
    public QiitaRepository provideQiitaRepository(QiitaClient qiitaClient, QiitaDao qiitaDao) {
        return new QiitaRepositoryImpl(qiitaClient, qiitaDao);
    }

    @Provides
    public QiitaClient provideQiitaClient(QiitaClient.QiitaService qiitaService) {
        return new QiitaClient(qiitaService);
    }

    @Provides
    public QiitaClient.QiitaService provideQiitaService() {
        return ApiClientGenerator.generate(
                QiitaClient.QiitaService.class,
                "https://qiita.com");
    }

    @Provides
    public QiitaDao provideQiitaDao(OrmaDatabaseWrapper ormaDatabaseWrapper) {
        return new QiitaDao(ormaDatabaseWrapper.getDatabase());
    }

}
