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

    private val accounts = BehaviorRelay.createDefault(loadAccounts())
    private var current = BehaviorRelay.createDefault(Pack(accounts.value.firstOrNull()))

    private fun loadAccounts(): List<Account> {
        val preference = PreferenceManager.getDefaultSharedPreferences(application)
        val accounts = preference.getStringSet("accounts", hashSetOf())
        return accounts.map {
            val accountPreference = application.getSharedPreferences(it, Context.MODE_PRIVATE)
            val token = accountPreference.getString("token", null)
            val secret = accountPreference.getString("secret", null)
            val userId = accountPreference.getLong("user_id", 0L)
            val userName = accountPreference.getString("user_name", null)
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
                    putString("token", account.twitter.authToken.token)
                    putString("secret", account.twitter.authToken.secret)
                    putLong("user_id", account.twitter.userId)
                    putString("user_name", account.twitter.userName)
                }
                .apply()

        val preference = PreferenceManager.getDefaultSharedPreferences(application)
        val accounts = preference.getStringSet("accounts", hashSetOf())
        accounts.add(account.twitter.userId.toString())
        preference.edit().clear().apply()
        preference.edit().putStringSet("accounts", accounts).apply()
    }

    private fun delete(account: Account) {
        val accountPreference = application.getSharedPreferences(
                account.twitter.userId.toString(),
                Context.MODE_PRIVATE)
        accountPreference.edit().clear().apply()

        val preference = PreferenceManager.getDefaultSharedPreferences(application)
        val accounts = preference.getStringSet("accounts", hashSetOf())
        accounts.remove(account.twitter.userId.toString())
        preference.edit().clear().apply()
        preference.edit().putStringSet("accounts", accounts).apply()
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