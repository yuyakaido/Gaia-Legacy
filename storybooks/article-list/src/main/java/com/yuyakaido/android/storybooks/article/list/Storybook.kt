package com.yuyakaido.android.storybooks.article.list

import com.yuyakaido.android.gaia.core.domain.app.GaiaType
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class Storybook : GaiaType() {
  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerStorybookComponent.factory()
      .storybookComponent(this)
  }

}