package com.yuyakaido.android.blueprint.di

import android.app.Application
import com.yuyakaido.android.blueprint.domain.LoggedInAccount
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {

    @AppScope
    @Provides
    fun provideApplication() = application

    @AppScope
    @Provides
    fun provideLoggedInAccount() = LoggedInAccount(application)

}