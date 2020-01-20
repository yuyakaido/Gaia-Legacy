package com.yuyakaido.android.storybooks.article.list

import android.app.Application
import com.yuyakaido.android.gaia.article.list.ArticleListModule
import com.yuyakaido.android.gaia.core.domain.app.AppScope
import com.yuyakaido.android.gaia.core.domain.app.GaiaType
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@AppScope
@Component(
  modules = [
    AndroidInjectionModule::class,
    ArticleListFragmentModule::class,
    StorybookModule::class
  ]
)
interface StorybookComponent : AndroidInjector<GaiaType> {
  @Component.Factory
  interface Factory {
    fun storybookComponent(@BindsInstance application: Application): StorybookComponent
  }
}