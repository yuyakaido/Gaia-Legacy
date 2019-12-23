package com.yuyakaido.android.gaia.core.infrastructure

import com.yuyakaido.android.gaia.core.domain.app.AppScope
import com.yuyakaido.android.gaia.core.domain.entity.Me
import javax.inject.Inject

@AppScope
class MeRepository @Inject constructor(
  private val api: PrivateApi
) {

  suspend fun me(): Me {
    return api.me().toEntity()
  }

}