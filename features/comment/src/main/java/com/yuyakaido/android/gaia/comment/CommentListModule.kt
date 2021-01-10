package com.yuyakaido.android.gaia.comment

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class CommentListModule {

  @Provides
  fun provideUser(
    fragment: CommentListFragment
  ): CommentListSource {
    return fragment.getCommentListSource()
  }

}