package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.Gaia
import com.yuyakaido.android.gaia.core.AppScope
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@AppScope
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class
])
interface AppComponent : AndroidInjector<Gaia> {
    @Component.Builder
    interface Builder {
        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }
    fun sessionComponentBuilder(): SessionComponent.Builder
}