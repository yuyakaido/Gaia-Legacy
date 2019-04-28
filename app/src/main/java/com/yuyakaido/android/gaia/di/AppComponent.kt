package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.Gaia
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
    abstract class Builder : AndroidInjector.Builder<Gaia>() {
        abstract fun appModule(appModule: AppModule): Builder
    }
    fun sessionComponentBuilder(): SessionComponent.Builder
}