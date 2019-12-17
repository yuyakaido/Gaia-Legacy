package com.yuyakaido.android.gaia.core.domain.app

import android.app.Application
import com.yuyakaido.android.gaia.core.domain.value.AccessToken
import javax.inject.Inject

class NoopAccessTokenService @Inject constructor(
  override val application: Application
) : AccessTokenServiceType {

  override fun current(): AccessToken {
    throw UnsupportedOperationException()
  }

  override fun save(token: AccessToken) {
    throw UnsupportedOperationException()
  }

  override fun delete() {
    throw UnsupportedOperationException()
  }
}