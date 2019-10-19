package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.Gaia
import com.yuyakaido.android.gaia.core.java.AppScope
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector

@AppScope
@Component(
  modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    AppActivityModule::class,
    AppDialogModule::class
  ]
)
interface AppComponent : AndroidInjector<Gaia> {
  @Component.Builder
  interface Builder {
    fun appModule(appModule: AppModule): Builder
    fun build(): AppComponent
  }

  fun androidInjector(): DispatchingAndroidInjector<Any>
  fun sessionComponentBuilder(): SessionComponent.Builder
}