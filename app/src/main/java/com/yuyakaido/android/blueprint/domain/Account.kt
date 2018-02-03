package com.yuyakaido.android.blueprint.domain

import android.app.Application
import com.twitter.sdk.android.core.TwitterSession
import com.yuyakaido.android.blueprint.app.Blueprint
import com.yuyakaido.android.blueprint.di.account.AccountModule
import com.yuyakaido.android.blueprint.infra.AccountPreference
import com.yuyakaido.android.blueprint.infra.AppPreference
import com.yuyakaido.android.blueprint.infra.TwitterRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class Account(val twitter: TwitterSession, application: Application) {

    @Inject
    lateinit var repository: TwitterRepository

    @Inject
    lateinit var appPreference: AppPreference

    @Inject
    lateinit var accountPreference: AccountPreference

    @Inject
    lateinit var disposables: CompositeDisposable

    init {
        (application as Blueprint).component
                .newAccountComponent(AccountModule(application, this))
                .inject(this)
    }

    fun save() {
        accountPreference.save(this)
        appPreference.save(this)
    }

    fun delete() {
        accountPreference.delete(this)
        appPreference.delete(this)
    }

    fun onLoggedOut() {
        disposables.dispose()
    }

}