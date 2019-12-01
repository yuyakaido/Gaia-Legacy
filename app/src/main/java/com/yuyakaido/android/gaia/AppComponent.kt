package com.yuyakaido.android.gaia

import dagger.Component

@Component(modules = [NetworkModule::class])
interface AppComponent {
  fun inject(gaia: Gaia)
}