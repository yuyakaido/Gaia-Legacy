package com.yuyakaido.android.gaia.core.domain.app

import android.app.Application
import com.yuyakaido.android.gaia.core.domain.value.AccessToken

interface AccessTokenServiceType {
  val application: Application
  fun current(): AccessToken
  fun save(token: AccessToken)
  fun delete()
}