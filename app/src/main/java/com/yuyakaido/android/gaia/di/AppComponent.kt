package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.Gaia
import com.yuyakaido.android.gaia.github.infra.di.ClientModule
import com.yuyakaido.android.gaia.github.infra.di.RepositoryModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ClientModule::class,
    RepositoryModule::class,
    ActivityModule::class,
    FragmentModule::class
])
interface AppComponent : AndroidInjector<Gaia> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<Gaia>()
}