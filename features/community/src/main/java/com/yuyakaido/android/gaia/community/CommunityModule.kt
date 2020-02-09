package com.yuyakaido.android.gaia.community

import com.yuyakaido.android.gaia.core.domain.app.AppScope
import com.yuyakaido.android.gaia.core.domain.repository.CommunityRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPrivate
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class CommunityModule {

  @AppScope
  @Provides
  fun provideCommunityApi(
    @RetrofitForPrivate retrofit: Retrofit
  ): CommunityApi {
    return retrofit.create(CommunityApi::class.java)
  }

  @AppScope
  @Provides
  fun provideCommunityRepositoryType(
    api: CommunityApi
  ): CommunityRepositoryType {
    return CommunityRepository(
      api = api
    )
  }

}