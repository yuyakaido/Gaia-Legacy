package com.yuyakaido.android.genesis.domain.module;

import com.yuyakaido.android.genesis.domain.repository.QiitaRepository;
import com.yuyakaido.android.genesis.domain.usecase.QiitaUseCase;
import com.yuyakaido.android.genesis.domain.usecase.QiitaUseCaseImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuyakaido on 3/12/16.
 */
@Module
public class QiitaDomainModule {

    @Provides
    @Singleton
    public QiitaUseCase provideQiitaUseCase(QiitaRepository qiitaRepository) {
        return new QiitaUseCaseImpl(qiitaRepository);
    }

}
