package com.yuyakaido.android.blueprint.di.module;

import com.yuyakaido.android.blueprint.domain.repository.PixabayRepository;
import com.yuyakaido.android.blueprint.domain.usecase.PixabayUseCase;
import com.yuyakaido.android.blueprint.domain.usecase.PixabayUseCaseImpl;
import com.yuyakaido.android.blueprint.infra.client.PixabayClient;
import com.yuyakaido.android.blueprint.infra.client.common.ApiClientGenerator;
import com.yuyakaido.android.blueprint.infra.dao.PixabayDao;
import com.yuyakaido.android.blueprint.infra.dao.common.OrmaDatabaseWrapper;
import com.yuyakaido.android.blueprint.infra.repository.PixabayRepositoryImpl;

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
