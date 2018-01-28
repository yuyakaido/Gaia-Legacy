package com.yuyakaido.android.blueprint.domain

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.jakewharton.rxrelay2.BehaviorRelay
import com.twitter.sdk.android.core.TwitterAuthToken
import com.twitter.sdk.android.core.TwitterSession
import com.yuyakaido.android.blueprint.misc.Pack
import io.reactivex.Observable
import javax.inject.Inject

class LoggedInAccount @Inject constructor(
        private val application: Application) {

    companion object {
        const val ACCOUNTS = "accounts"
        const val TOKEN = "token"
        const val SECRET = "secret"
        const val USER_ID = "user_id"
        const val USER_NAME = "user_name"
    }

    private val accounts = BehaviorRelay.createDefault(loadAccounts())
    private var current = BehaviorRelay.createDefault(Pack(accounts.value.firstOrNull()))

    private fun loadAccounts(): List<Account> {
        val preference = PreferenceManager.getDefaultSharedPreferences(application)
        val accounts = preference.getStringSet(ACCOUNTS, hashSetOf())
        return accounts.map {
            val accountPreference = application.getSharedPreferences(it, Context.MODE_PRIVATE)
            val token = accountPreference.getString(TOKEN, null)
            val secret = accountPreference.getString(SECRET, null)
            val userId = accountPreference.getLong(USER_ID, 0L)
            val userName = accountPreference.getString(USER_NAME, null)
            val autoToken = TwitterAuthToken(token, secret)
            val session = TwitterSession(autoToken, userId, userName)
            return@map Account(session, application)
        }
    }

    private fun save(account: Account) {
        val accountPreference = application.getSharedPreferences(
                account.twitter.userId.toString(),
                Context.MODE_PRIVATE)
        accountPreference.edit()
                .apply {
                    putString(TOKEN, account.twitter.authToken.token)
                    putString(SECRET, account.twitter.authToken.secret)
                    putLong(USER_ID, account.twitter.userId)
                    putString(USER_NAME, account.twitter.userName)
                }
                .apply()

        val preference = PreferenceManager.getDefaultSharedPreferences(application)
        val accounts = preference.getStringSet(ACCOUNTS, hashSetOf())
        accounts.add(account.twitter.userId.toString())
        preference.edit().clear().apply()
        preference.edit().putStringSet(ACCOUNTS, accounts).apply()
    }

    private fun delete(account: Account) {
        val accountPreference = application.getSharedPreferences(
                account.twitter.userId.toString(),
                Context.MODE_PRIVATE)
        accountPreference.edit().clear().apply()

        val preference = PreferenceManager.getDefaultSharedPreferences(application)
        val accounts = preference.getStringSet(ACCOUNTS, hashSetOf())
        accounts.remove(account.twitter.userId.toString())
        preference.edit().clear().apply()
        preference.edit().putStringSet(ACCOUNTS, accounts).apply()
    }

    fun current(): Observable<Pack<Account?>> {
        return current
    }

    fun switchTo(index: Int) {
        if (current.hasValue() && current.value.value?.twitter?.userId != accounts.value[index].twitter.userId) {
            current.accept(Pack(accounts.value[index]))
        }
    }

    fun add(account: Account) {
        accounts.value.forEach {
            if (it.twitter.userId == account.twitter.userId) {
                return
            }
        }

        save(account)
        accounts.accept(accounts.value.plus(account))
        current.accept(Pack(account))
    }

    fun remove(account: Account) {
        delete(account)
        accounts.accept(accounts.value.minus(account))
        if (accounts.value.isEmpty()) {
            current.accept(Pack(null))
        } else {
            current.accept(Pack(accounts.value.first()))
        }
    }

    fun accounts(): Observable<List<Account>> {
        return accounts
    }

}