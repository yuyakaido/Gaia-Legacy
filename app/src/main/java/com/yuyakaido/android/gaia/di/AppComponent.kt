package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.Gaia
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    ServiceModule::class,
    ActivityModule::class,
    FragmentModule::class
])
interface AppComponent : AndroidInjector<Gaia> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<Gaia>()
}