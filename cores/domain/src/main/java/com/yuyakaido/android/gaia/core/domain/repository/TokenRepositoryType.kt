package com.yuyakaido.android.gaia.core.domain.repository

import com.yuyakaido.android.gaia.core.domain.entity.Session
import com.yuyakaido.android.gaia.core.domain.value.AuthToken

interface TokenRepositoryType {
  suspend fun get(code: String): AuthToken
  suspend fun refresh(session: Session): AuthToken
}