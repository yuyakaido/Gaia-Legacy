package com.yuyakaido.android.gaia.core.domain.app

import android.app.Application
import com.yuyakaido.android.gaia.core.domain.value.AuthToken

interface AuthTokenServiceType {
  val application: Application
  fun current(): AuthToken
  fun save(token: AuthToken)
  fun delete()
}