package com.yuyakaido.android.storybook

import com.yuyakaido.android.gaia.article.detail.ArticleDetailActivity
import com.yuyakaido.android.gaia.article.detail.ArticleDetailModule
import com.yuyakaido.android.gaia.comment.list.CommentListFragment
import com.yuyakaido.android.gaia.comment.list.CommentListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FeatureModule {

  @ContributesAndroidInjector(modules = [ArticleDetailModule::class])
  abstract fun provideArticleDetailActivity(): ArticleDetailActivity

  @ContributesAndroidInjector(modules = [CommentListModule::class])
  abstract fun provideCommentListFragment(): CommentListFragment
}