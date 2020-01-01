package com.yuyakaido.android.gaia.comment.list

import dagger.Module
import dagger.Provides

@Module
class CommentListModule {

  @Provides
  fun provideUser(
    fragment: CommentListFragment
  ): CommentListSource {
    return fragment.getCommentListSource()
  }

}