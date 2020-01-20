package com.yuyakaido.android.storybook.article.list

import androidx.fragment.app.Fragment
import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import com.yuyakaido.android.gaia.article.list.ArticleListModule
import com.yuyakaido.android.gaia.core.presentation.BaseFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FeatureModule {

  @ContributesAndroidInjector(modules = [ArticleListModule::class])
  abstract fun provideArticleListFragment(): BaseFragment
}