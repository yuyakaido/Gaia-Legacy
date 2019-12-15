package com.yuyakaido.android.gaia.auth

import android.app.Application
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.yuyakaido.android.gaia.core.domain.app.AccessTokenServiceType
import com.yuyakaido.android.gaia.core.domain.value.AccessToken

class AccessTokenService(
  override val application: Application
) : AccessTokenServiceType {

  companion object {
    private const val ACCESS_TOKEN = "access_token"
  }

  private val preference = PreferenceManager.getDefaultSharedPreferences(application)

  override fun current(): AccessToken {
    return AccessToken(value = preference.getString(ACCESS_TOKEN, null))
  }

  override fun save(token: AccessToken) {
    preference.edit(commit = false) {
      putString(ACCESS_TOKEN, token.rawValue())
    }
  }

  override fun delete() {
    preference.edit(commit = false) {
      putString(ACCESS_TOKEN, null)
    }
  }

}