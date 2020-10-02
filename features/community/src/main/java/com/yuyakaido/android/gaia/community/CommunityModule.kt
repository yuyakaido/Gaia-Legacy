package com.yuyakaido.android.gaia.community

import com.yuyakaido.android.gaia.core.domain.app.SessionScope
import com.yuyakaido.android.gaia.core.domain.repository.CommunityRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPrivate
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class CommunityModule {

  @SessionScope
  @Provides
  fun provideCommunityApi(
    @RetrofitForPrivate retrofit: Retrofit
  ): CommunityApi {
    return retrofit.create(CommunityApi::class.java)
  }

  @SessionScope
  @Provides
  fun provideCommunityRepositoryType(
    api: CommunityApi
  ): CommunityRepositoryType {
    return CommunityRepository(
      api = api
    )
  }

}