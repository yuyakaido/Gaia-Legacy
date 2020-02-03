package com.yuyakaido.android.gaia.core.domain.repository

import com.yuyakaido.android.gaia.core.domain.value.AuthToken

interface TokenRepositoryType {
  suspend fun get(code: String): AuthToken
  suspend fun get(): AuthToken
  suspend fun refresh(): AuthToken
  suspend fun save(token: AuthToken)
}