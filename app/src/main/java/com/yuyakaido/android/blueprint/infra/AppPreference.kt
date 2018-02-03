package com.yuyakaido.android.blueprint.infra

import android.app.Application
import android.content.SharedPreferences
import com.yuyakaido.android.blueprint.domain.Account
import javax.inject.Inject

class AppPreference @Inject constructor(
        private val application: Application,
        private val preference: SharedPreferences) {

    companion object {
        const val ACCOUNTS = "accounts"
    }

    fun accounts(): List<Account> {
        val accounts = preference.getStringSet(ACCOUNTS, hashSetOf())
        return accounts.map { AccountPreference.valueOf(application, it).load(application, it) }
    }

    fun save(account: Account) {
        val accounts = preference.getStringSet(ACCOUNTS, hashSetOf())
        accounts.add(account.twitter.userId.toString())
        preference.edit().clear().apply()
        preference.edit().putStringSet(ACCOUNTS, accounts).apply()
    }

    fun delete(account: Account) {
        val accounts = preference.getStringSet(ACCOUNTS, hashSetOf())
        accounts.remove(account.twitter.userId.toString())
        preference.edit().clear().apply()
        preference.edit().putStringSet(ACCOUNTS, accounts).apply()
    }

}