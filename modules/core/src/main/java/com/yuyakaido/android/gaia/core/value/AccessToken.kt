package com.yuyakaido.android.gaia.core.value

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager

data class AccessToken(
  private val value: String?
) {

  companion object {
    private const val ACCESS_TOKEN = "access_token"

    fun current(context: Context): AccessToken {
      val preference = PreferenceManager.getDefaultSharedPreferences(context)
      return AccessToken(
        value = preference.getString(ACCESS_TOKEN, null)
      )
    }

    fun delete(context: Context) {
      val preference = PreferenceManager.getDefaultSharedPreferences(context)
      preference.edit(commit = false) {
        putString(ACCESS_TOKEN, null)
      }
    }
  }

  fun save(context: Context) {
    val preference = PreferenceManager.getDefaultSharedPreferences(context)
    preference.edit(commit = false) {
      putString(ACCESS_TOKEN, value)
    }
  }

  fun isLoggedIn(): Boolean {
    return value != null
  }

  fun value(): String {
    return "bearer $value"
  }

}