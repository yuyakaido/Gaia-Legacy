package com.yuyakaido.android.gaia

import android.app.Application
import com.yuyakaido.android.gaia.core.domain.app.AppScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@AppScope
@Component(modules = [
  AndroidInjectionModule::class,
  AppModule::class
])
interface AppComponent : AndroidInjector<Gaia> {
  @Component.Factory
  interface Factory {
    fun create(@BindsInstance application: Application): AppComponent
  }

  fun newSignedOutSessionComponentFactory(): SignedOutSessionComponent.Factory
  fun newSignedInSessionComponentFactory(): SignedInSessionComponent.Factory
}