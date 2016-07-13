package com.yuyakaido.android.genesis.domain.module;

import com.yuyakaido.android.genesis.domain.repository.PixabayRepository;
import com.yuyakaido.android.genesis.domain.usecase.PixabayUseCase;
import com.yuyakaido.android.genesis.domain.usecase.PixabayUseCaseImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yuyakaido on 3/6/16.
 */
@Module
public class PixabayDomainModule {

    @Provides
    public PixabayUseCase providePixabayUseCase(PixabayRepository pixabayRepository) {
        return new PixabayUseCaseImpl(pixabayRepository);
    }

}
