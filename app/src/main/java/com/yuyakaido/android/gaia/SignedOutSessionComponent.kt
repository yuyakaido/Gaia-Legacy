package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.auth.SignedOutAuthModule
import com.yuyakaido.android.gaia.core.domain.app.SignedOutScope
import dagger.Subcomponent
import dagger.android.DispatchingAndroidInjector

@SignedOutScope
@Subcomponent(
  modules = [
    // Core
    SignedOutNetworkModule::class,
    SignedOutActivityModule::class,

    // Feature
    SignedOutAuthModule::class
  ]
)
interface SignedOutSessionComponent {
  @Subcomponent.Builder
  interface Builder {
    fun build(): SignedOutSessionComponent
  }

  fun androidInjector(): DispatchingAndroidInjector<Any>
}