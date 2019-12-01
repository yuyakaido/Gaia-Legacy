package com.yuyakaido.android.gaia

import dagger.Component

@AppScope
@Component(modules = [NetworkModule::class])
interface AppComponent {
  fun inject(gaia: Gaia)
}
