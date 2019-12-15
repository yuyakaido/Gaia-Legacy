package com.yuyakaido.android.gaia.core.infrastructure

import com.yuyakaido.android.gaia.core.app.AppScope
import com.yuyakaido.android.gaia.core.domain.entity.Me
import javax.inject.Inject

@AppScope
class MeRepository @Inject constructor(
  private val service: RedditAuthService
) {

  suspend fun me(): Me {
    return service.me().toEntity()
  }

}