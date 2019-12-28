package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import com.yuyakaido.android.gaia.article.list.ArticleListModule
import com.yuyakaido.android.gaia.comment.list.CommentListFragment
import com.yuyakaido.android.gaia.comment.list.CommentListModule
import com.yuyakaido.android.gaia.search.SearchFragment
import com.yuyakaido.android.gaia.user.detail.UserDetailFragment
import com.yuyakaido.android.gaia.user.detail.UserDetailModule
import com.yuyakaido.android.gaia.user.list.UserListFragment
import com.yuyakaido.android.gaia.user.list.UserListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

  @ContributesAndroidInjector(
    modules = [ArticleListModule::class]
  )
  abstract fun contributeArticleListFragment(): ArticleListFragment

  @ContributesAndroidInjector(
    modules = [CommentListModule::class]
  )
  abstract fun contributeCommentListFragment(): CommentListFragment

  @ContributesAndroidInjector(
    modules = [UserListModule::class]
  )
  abstract fun contributeUserListFragment(): UserListFragment

  @ContributesAndroidInjector(
    modules = [UserDetailModule::class]
  )
  abstract fun contributeUserDetailFragment(): UserDetailFragment

  @ContributesAndroidInjector
  abstract fun contributeSearchFragment(): SearchFragment

}