package com.yuyakaido.android.blueprint.domain

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.twitter.sdk.android.core.TwitterAuthToken
import com.twitter.sdk.android.core.TwitterSession
import com.yuyakaido.android.blueprint.app.Blueprint
import com.yuyakaido.android.blueprint.di.AccountModule
import com.yuyakaido.android.blueprint.infra.TwitterRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class Account(
        val twitter: TwitterSession,
        val application: Application,
        val disposables: CompositeDisposable = CompositeDisposable()) {

    companion object {
        const val TOKEN = "token"
        const val SECRET = "secret"
        const val USER_ID = "user_id"
        const val USER_NAME = "user_name"

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
    }

    @Inject
    lateinit var repository: TwitterRepository

    @Inject
    lateinit var accountPreference: SharedPreferences

    init {
        (application as Blueprint).component
                .newAccountComponent(AccountModule(application, this))
                .inject(this)
    }

    fun save() {
        accountPreference.edit()
                .apply {
                    putString(TOKEN, twitter.authToken.token)
                    putString(SECRET, twitter.authToken.secret)
                    putLong(USER_ID, twitter.userId)
                    putString(USER_NAME, twitter.userName)
                }
                .apply()

        val preference = PreferenceManager.getDefaultSharedPreferences(application)
        val accounts = preference.getStringSet(LoggedInAccount.ACCOUNTS, hashSetOf())
        accounts.add(twitter.userId.toString())
        preference.edit().clear().apply()
        preference.edit().putStringSet(LoggedInAccount.ACCOUNTS, accounts).apply()
    }

    fun delete() {
        accountPreference.edit().clear().apply()

        val preference = PreferenceManager.getDefaultSharedPreferences(application)
        val accounts = preference.getStringSet(LoggedInAccount.ACCOUNTS, hashSetOf())
        accounts.remove(twitter.userId.toString())
        preference.edit().clear().apply()
        preference.edit().putStringSet(LoggedInAccount.ACCOUNTS, accounts).apply()
    }

    fun onLoggedOut() {
        disposables.dispose()
    }

}