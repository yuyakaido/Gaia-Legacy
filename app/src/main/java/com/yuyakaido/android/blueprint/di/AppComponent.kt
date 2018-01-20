package com.yuyakaido.android.blueprint.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class))
interface AppComponent