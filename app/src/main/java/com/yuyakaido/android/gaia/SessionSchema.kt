package com.yuyakaido.android.gaia

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yuyakaido.android.gaia.core.domain.entity.Session
import com.yuyakaido.android.gaia.core.domain.value.AuthToken

@Entity
data class SessionSchema(
  @PrimaryKey val id: String,
  @ColumnInfo val accessToken: String,
  @ColumnInfo val refreshToken: String?
) {

  companion object {
    fun fromEntity(
      session: Session.SignedIn
    ): SessionSchema {
      return SessionSchema(
        id = session.id,
        accessToken = session.token.accessToken,
        refreshToken = session.refreshToken()
      )
    }

    fun fromEntity(
      oldSession: Session.SignedIn,
      newSession: Session.SignedIn
    ): SessionSchema {
      return SessionSchema(
        id = newSession.id,
        accessToken = newSession.token.accessToken,
        refreshToken = oldSession.refreshToken()
      )
    }
  }

  fun toEntity(): Session.SignedIn {
    return Session.SignedIn(
      id = id,
      token = AuthToken(
        accessToken = accessToken,
        refreshToken = refreshToken
      )
    )
  }

}