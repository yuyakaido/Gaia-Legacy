package com.yuyakaido.android.gaia.core.domain.app

import com.yuyakaido.android.gaia.core.domain.value.AuthToken

class NoopTokenRepository : TokenRepositoryType {

  override suspend fun get(code: String): AuthToken {
    throw UnsupportedOperationException()
  }

  override suspend fun get(): AuthToken {
    throw UnsupportedOperationException()
  }

  override suspend fun refresh(): AuthToken {
    throw UnsupportedOperationException()
  }

  override suspend fun save(token: AuthToken) {
    throw UnsupportedOperationException()
  }
}