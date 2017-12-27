package com.yuyakaido.android.blueprint.di

import com.yuyakaido.android.blueprint.Blueprint
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        AppModule::class))
interface AppComponent : AndroidInjector<Blueprint> {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }

}