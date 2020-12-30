package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.article.ArticleModule
import com.yuyakaido.android.gaia.auth.SignedInAuthModule
import com.yuyakaido.android.gaia.comment.CommentModule
import com.yuyakaido.android.gaia.community.CommunityModule
import com.yuyakaido.android.gaia.core.domain.app.SignedInScope
import com.yuyakaido.android.gaia.search.SearchModule
import com.yuyakaido.android.gaia.user.UserModule
import com.yuyakaido.android.gaia.vote.VoteModule
import dagger.Subcomponent
import dagger.android.DispatchingAndroidInjector

@SignedInScope
@Subcomponent(
  modules = [
    SignedInSessionModule::class,

    // Core
    SignedInNetworkModule::class,
    SignedInActivityModule::class,
    SignedInFragmentModule::class,

    // Feature
    SignedInAuthModule::class,
    ArticleModule::class,
    CommentModule::class,
    CommunityModule::class,
    UserModule::class,
    SearchModule::class,
    VoteModule::class
  ]
)
interface SignedInSessionComponent {
  @Subcomponent.Factory
  interface Factory {
    fun create(module: SignedInSessionModule): SignedInSessionComponent
  }

  fun androidInjector(): DispatchingAndroidInjector<Any>
}