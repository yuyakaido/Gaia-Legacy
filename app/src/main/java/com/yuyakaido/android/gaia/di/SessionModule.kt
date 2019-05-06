package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.core.Session
import dagger.Module
import dagger.Provides

@Module
class SessionModule {

    @SessionScope
    @Provides
    fun provideSession(): Session {
        return Session()
    }

}