package com.yuyakaido.android.gaia.core.domain.repository

import com.yuyakaido.android.gaia.core.domain.entity.Session

interface SessionRepositoryType {
  suspend fun get(): List<Session.SignedIn>
  suspend fun get(id: String): Session.SignedIn
  suspend fun post(session: Session.SignedIn)
  suspend fun put(oldSession: Session.SignedIn, newSession: Session.SignedIn)
}