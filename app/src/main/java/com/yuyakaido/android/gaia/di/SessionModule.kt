package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.core.java.Environment
import com.yuyakaido.android.gaia.core.java.SessionState
import dagger.Module
import dagger.Provides

@Module
class SessionModule(private val session: SessionState) {

    @SessionScope
    @Provides
    fun provideEnvironment(): Environment {
        return session.environment
    }

    @SessionScope
    @Provides
    fun provideSession(): SessionState {
        return session
    }

}