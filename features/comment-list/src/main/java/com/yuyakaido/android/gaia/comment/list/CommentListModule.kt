package com.yuyakaido.android.gaia.comment.list

import com.yuyakaido.android.gaia.core.domain.entity.User
import dagger.Module
import dagger.Provides

@Module
class CommentListModule {

  @Provides
  fun provideUser(
    fragment: CommentListFragment
  ): User {
    return fragment.getUser()
  }

}