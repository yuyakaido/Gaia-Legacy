package com.yuyakaido.android.gaia.community.detail

import com.yuyakaido.android.gaia.core.domain.entity.Community
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class CommunityDetailModule {

  @Provides
  fun provideCommunity(
    fragment: CommunityDetailFragment
  ): Community.Summary {
    return fragment.args.community.toSummary()
  }

}