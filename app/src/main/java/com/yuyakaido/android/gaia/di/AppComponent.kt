package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.Gaia
import com.yuyakaido.android.gaia.data.di.ClientModule
import com.yuyakaido.android.gaia.data.di.RepositoryModule
import com.yuyakaido.android.gaia.ui.di.ActivityModule
import com.yuyakaido.android.gaia.ui.di.FragmentModule
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