package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.article.detail.ArticleDetailFragment
import com.yuyakaido.android.gaia.article.detail.ArticleDetailModule
import com.yuyakaido.android.gaia.article.list.ArticleListFragment
import com.yuyakaido.android.gaia.article.list.ArticleListModule
import com.yuyakaido.android.gaia.comment.CommentListFragment
import com.yuyakaido.android.gaia.comment.CommentListModule
import com.yuyakaido.android.gaia.community.detail.CommunityDetailFragment
import com.yuyakaido.android.gaia.community.detail.CommunityDetailModule
import com.yuyakaido.android.gaia.community.list.CommunityListFragment
import com.yuyakaido.android.gaia.search.SearchFragment
import com.yuyakaido.android.gaia.user.presentation.detail.UserDetailFragment
import com.yuyakaido.android.gaia.user.presentation.detail.UserDetailModule
import com.yuyakaido.android.gaia.user.presentation.list.UserListFragment
import com.yuyakaido.android.gaia.user.presentation.list.UserListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

  @ContributesAndroidInjector(
    modules = [ArticleListModule::class]
  )
  abstract fun contributeArticleListFragment(): ArticleListFragment

  @ContributesAndroidInjector(
    modules = [ArticleDetailModule::class]
  )
  abstract fun contributeArticleDetailFragment(): ArticleDetailFragment

  @ContributesAndroidInjector(
    modules = [CommentListModule::class]
  )
  abstract fun contributeCommentListFragment(): CommentListFragment

  @ContributesAndroidInjector
  abstract fun contributeCommunityListFragment(): CommunityListFragment

  @ContributesAndroidInjector(
    modules = [CommunityDetailModule::class]
  )
  abstract fun contributeCommunityDetailFragment(): CommunityDetailFragment

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