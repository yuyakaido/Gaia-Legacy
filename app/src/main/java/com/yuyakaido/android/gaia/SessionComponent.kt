package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.article.ArticleModule
import com.yuyakaido.android.gaia.auth.AuthModule
import com.yuyakaido.android.gaia.comment.CommentModule
import com.yuyakaido.android.gaia.community.CommunityModule
import com.yuyakaido.android.gaia.core.domain.app.SessionScope
import com.yuyakaido.android.gaia.search.SearchModule
import com.yuyakaido.android.gaia.user.UserModule
import dagger.Subcomponent
import dagger.android.DispatchingAndroidInjector

@SessionScope
@Subcomponent(
  modules = [
    // Core
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
  ]
)
interface SessionComponent {
  @Subcomponent.Builder
  interface Builder {
    fun build(): SessionComponent
  }

  fun androidInjector(): DispatchingAndroidInjector<Any>
}