package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.core.java.Environment
import com.yuyakaido.android.gaia.core.java.Session
import dagger.Module
import dagger.Provides

@Module
class SessionModule(private val environment: Environment) {

    @SessionScope
    @Provides
    fun provideEnvironment(): Environment {
        return environment
    }

    @SessionScope
    @Provides
    fun provideSession(): Session {
        return Session(
            environment = environment
        )
    }

}