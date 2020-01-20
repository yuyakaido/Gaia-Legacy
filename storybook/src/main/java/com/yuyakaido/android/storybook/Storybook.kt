package com.yuyakaido.android.storybook

import com.yuyakaido.android.gaia.core.domain.app.GaiaType
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class Storybook : GaiaType() {
  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerStorybookComponent
      .factory()
      .create(this)

  }
}
