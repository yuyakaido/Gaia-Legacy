package com.yuyakaido.android.gaia

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@AppScope
@Component(modules = [
  AndroidInjectionModule::class,
  ActivityModule::class,
  NetworkModule::class
])
interface AppComponent : AndroidInjector<Gaia> {
  @Component.Builder
  interface Builder {
    fun application(@BindsInstance application: Application): Builder
    fun build(): AppComponent
  }
}
