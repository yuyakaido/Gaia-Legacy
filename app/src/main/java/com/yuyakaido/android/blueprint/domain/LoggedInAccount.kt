package com.yuyakaido.android.blueprint.domain

import android.app.Application
import android.preference.PreferenceManager
import com.jakewharton.rxrelay2.BehaviorRelay
import com.yuyakaido.android.blueprint.misc.Pack
import io.reactivex.Observable
import javax.inject.Inject

class LoggedInAccount @Inject constructor(application: Application) {

    companion object {
        const val ACCOUNTS = "accounts"

        private fun accounts(application: Application): List<Account> {
            val preference = PreferenceManager.getDefaultSharedPreferences(application)
            val accounts = preference.getStringSet(ACCOUNTS, hashSetOf())
            return accounts.map { Account.load(application, it) }
        }
    }

    private val accounts = BehaviorRelay.createDefault(accounts(application))
    private var current = BehaviorRelay.createDefault(Pack(accounts.value.firstOrNull()))

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

        account.save()
        accounts.accept(accounts.value.plus(account))
        current.accept(Pack(account))
    }

    fun remove(account: Account) {
        account.onLoggedOut()
        account.delete()
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