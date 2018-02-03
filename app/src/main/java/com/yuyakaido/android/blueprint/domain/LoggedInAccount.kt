package com.yuyakaido.android.blueprint.domain

import com.jakewharton.rxrelay2.BehaviorRelay
import com.yuyakaido.android.blueprint.infra.AppPreference
import com.yuyakaido.android.blueprint.misc.Pack
import io.reactivex.Observable
import javax.inject.Inject

class LoggedInAccount @Inject constructor(preference: AppPreference) {

    private val accounts = BehaviorRelay.createDefault(preference.accounts())
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