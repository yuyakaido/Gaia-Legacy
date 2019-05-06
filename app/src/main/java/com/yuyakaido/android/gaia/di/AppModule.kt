package com.yuyakaido.android.gaia.di

import android.app.Application
import com.yuyakaido.android.gaia.core.AppStore
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {

    @AppScope
    @Provides
    fun provideApplication(): Application {
        return application
    }

    @AppScope
    @Provides
    fun provideAppStore(): AppStore {
        return AppStore()
    }

}