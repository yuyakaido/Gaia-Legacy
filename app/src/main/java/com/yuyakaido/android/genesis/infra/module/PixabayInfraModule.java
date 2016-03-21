package com.yuyakaido.android.genesis.infra.module;

import com.yuyakaido.android.genesis.domain.repository.PixabayRepository;
import com.yuyakaido.android.genesis.infra.client.PixabayClient;
import com.yuyakaido.android.genesis.infra.client.common.ApiClientGenerator;
import com.yuyakaido.android.genesis.infra.dao.PixabayDao;
import com.yuyakaido.android.genesis.infra.dao.common.OrmaDatabaseWrapper;
import com.yuyakaido.android.genesis.infra.repository.PixabayRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuyakaido on 3/6/16.
 */
@Module
public class PixabayInfraModule {

    @Provides
    @Singleton
    public PixabayRepository providePixabayRepository(PixabayClient pixabayClient, PixabayDao pixabayDao) {
        return new PixabayRepositoryImpl(pixabayClient, pixabayDao);
    }

    @Provides
    @Singleton
    public PixabayClient providePixabayClient(PixabayClient.PixabayService pixabayService) {
        return new PixabayClient(pixabayService);
    }

    @Provides
    @Singleton
    public PixabayClient.PixabayService providePixabayService() {
        return ApiClientGenerator.generate(
                PixabayClient.PixabayService.class,
                "https://pixabay.com");
    }

    @Provides
    @Singleton
    public PixabayDao providePixabayDao(OrmaDatabaseWrapper ormaDatabaseWrapper) {
        return new PixabayDao(ormaDatabaseWrapper.getDatabase());
    }

}
