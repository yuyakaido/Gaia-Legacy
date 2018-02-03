package com.yuyakaido.android.blueprint.infra

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.twitter.sdk.android.core.TwitterAuthToken
import com.twitter.sdk.android.core.TwitterSession
import com.yuyakaido.android.blueprint.domain.Account
import javax.inject.Inject

class AccountPreference @Inject constructor(
        private val preference: SharedPreferences) {

    companion object {
        const val TOKEN = "token"
        const val SECRET = "secret"
        const val USER_ID = "user_id"
        const val USER_NAME = "user_name"

        fun valueOf(application: Application, id: String): AccountPreference {
            return AccountPreference(application.getSharedPreferences(id, Context.MODE_PRIVATE))
        }
    }

    fun load(application: Application, id: String): Account {
        val accountPreference = application.getSharedPreferences(id, Context.MODE_PRIVATE)
        val token = accountPreference.getString(TOKEN, null)
        val secret = accountPreference.getString(SECRET, null)
        val userId = accountPreference.getLong(USER_ID, 0L)
        val userName = accountPreference.getString(USER_NAME, null)
        val autoToken = TwitterAuthToken(token, secret)
        val session = TwitterSession(autoToken, userId, userName)
        return Account(session, application)
    }

    fun save(account: Account) {
        preference.edit()
                .apply {
                    putString(TOKEN, account.twitter.authToken.token)
                    putString(SECRET, account.twitter.authToken.secret)
                    putLong(USER_ID, account.twitter.userId)
                    putString(USER_NAME, account.twitter.userName)
                }
                .apply()
    }

    fun delete(account: Account) {
        preference.edit().clear().apply()
    }

}