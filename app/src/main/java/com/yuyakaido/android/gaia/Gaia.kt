package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.core.domain.app.GaiaType
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class Gaia : GaiaType() {

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerAppComponent
      .builder()
      .application(this)
      .build()
  }

}