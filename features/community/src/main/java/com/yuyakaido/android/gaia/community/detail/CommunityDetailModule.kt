package com.yuyakaido.android.gaia.community.detail

import com.yuyakaido.android.gaia.core.domain.entity.Community
import dagger.Module
import dagger.Provides

@Module
class CommunityDetailModule {

  @Provides
  fun provideCommunity(
    activity: CommunityDetailActivity
  ): Community.Summary {
    return activity.args.community.toSummary()
  }

}