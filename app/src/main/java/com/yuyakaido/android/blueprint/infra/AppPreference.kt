package com.yuyakaido.android.blueprint.infra

import android.app.Application
import android.content.SharedPreferences
import com.yuyakaido.android.blueprint.domain.Account
import org.json.JSONArray
import javax.inject.Inject

class AppPreference @Inject constructor(
        private val application: Application,
        private val preference: SharedPreferences) {

    companion object {
        const val ACCOUNTS = "accounts"
    }

    fun accounts(): List<Account> {
        val raw = preference.getString(ACCOUNTS, null)
        return if (raw == null) {
            listOf()
        } else {
            val accounts = JSONArray(raw)
            IntRange(0, accounts.length() - 1)
                    .map { accounts.getLong(it) }
                    .map { AccountPreference.valueOf(application, it).load(application, it) }
                    .apply { forEach { it.open(application) } }
        }
    }

    fun save(account: Account) {
        val raw = preference.getString(ACCOUNTS, null)
        val accounts = if (raw == null) {
            JSONArray()
        } else {
            JSONArray(raw)
        }
        accounts.put(account.twitter.userId)
        preference.edit().putString(ACCOUNTS, accounts.toString()).apply()
    }

    fun delete(account: Account) {
        val raw = preference.getString(ACCOUNTS, null)
        val accounts = if (raw == null) {
            JSONArray()
        } else {
            JSONArray(raw)
        }
        IntRange(0, accounts.length() - 1).forEach {
            if (accounts[it] == account.twitter.id) {
                accounts.remove(it)
            }
        }
        preference.edit().putString(ACCOUNTS, accounts.toString()).apply()
    }

}