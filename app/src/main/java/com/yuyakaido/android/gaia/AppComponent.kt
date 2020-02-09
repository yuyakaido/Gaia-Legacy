package com.yuyakaido.android.gaia

import android.app.Application
import com.yuyakaido.android.gaia.article.ArticleModule
import com.yuyakaido.android.gaia.auth.AuthModule
import com.yuyakaido.android.gaia.comment.CommentModule
import com.yuyakaido.android.gaia.community.CommunityModule
import com.yuyakaido.android.gaia.core.domain.app.AppScope
import com.yuyakaido.android.gaia.search.SearchModule
import com.yuyakaido.android.gaia.user.UserModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@AppScope
@Component(modules = [
  // App
  AndroidInjectionModule::class,
  AppModule::class,
  NetworkModule::class,
  FragmentModule::class,
  ActivityModule::class,

  // Feature
  AuthModule::class,
  ArticleModule::class,
  CommentModule::class,
  CommunityModule::class,
  UserModule::class,
  SearchModule::class
])
interface AppComponent : AndroidInjector<Gaia> {
  @Component.Builder
  interface Builder {
    fun application(@BindsInstance application: Application): Builder
    fun build(): AppComponent
  }
}