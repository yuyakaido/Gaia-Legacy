package com.yuyakaido.android.blueprint.di

import android.app.Application
import com.yuyakaido.android.blueprint.domain.LoggedInAccount
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideApplication() = application

    @Singleton
    @Provides
    fun provideRunningSession() = LoggedInAccount(application)

}