package com.yuyakaido.android.genesis.di.module;

import com.yuyakaido.android.genesis.domain.repository.PixabayRepository;
import com.yuyakaido.android.genesis.domain.usecase.PixabayUseCase;
import com.yuyakaido.android.genesis.domain.usecase.PixabayUseCaseImpl;
import com.yuyakaido.android.genesis.infra.client.PixabayClient;
import com.yuyakaido.android.genesis.infra.client.common.ApiClientGenerator;
import com.yuyakaido.android.genesis.infra.dao.PixabayDao;
import com.yuyakaido.android.genesis.infra.dao.common.OrmaDatabaseWrapper;
import com.yuyakaido.android.genesis.infra.repository.PixabayRepositoryImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuyakaido on 3/6/16.
 */
@Module
public class PixabayModule {

    @Provides
    public PixabayUseCase providePixabayUseCase(PixabayRepository pixabayRepository) {
        return new PixabayUseCaseImpl(pixabayRepository);
    }

    @Provides
    public PixabayRepository providePixabayRepository(PixabayClient pixabayClient, PixabayDao pixabayDao) {
        return new PixabayRepositoryImpl(pixabayClient, pixabayDao);
    }

    @Provides
    public PixabayClient providePixabayClient(PixabayClient.PixabayService pixabayService) {
        return new PixabayClient(pixabayService);
    }

    @Provides
    public PixabayClient.PixabayService providePixabayService() {
        return ApiClientGenerator.generate(
                PixabayClient.PixabayService.class,
                "https://pixabay.com");
    }

    @Provides
    public PixabayDao providePixabayDao(OrmaDatabaseWrapper ormaDatabaseWrapper) {
        return new PixabayDao(ormaDatabaseWrapper.getDatabase());
    }

}
