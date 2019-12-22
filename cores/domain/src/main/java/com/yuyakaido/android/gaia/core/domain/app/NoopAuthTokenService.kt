package com.yuyakaido.android.gaia.core.domain.app

import android.app.Application
import com.yuyakaido.android.gaia.core.domain.value.AuthToken
import javax.inject.Inject

class NoopAuthTokenService @Inject constructor(
  override val application: Application
) : AuthTokenServiceType {

  override fun current(): AuthToken {
    throw UnsupportedOperationException()
  }

  override fun save(token: AuthToken) {
    throw UnsupportedOperationException()
  }

}