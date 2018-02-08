package com.yuyakaido.android.blueprint.domain

import android.app.Application
import com.twitter.sdk.android.core.TwitterSession
import com.yuyakaido.android.blueprint.app.Blueprint
import com.yuyakaido.android.blueprint.di.account.AccountModule
import com.yuyakaido.android.blueprint.infra.AccountPreference
import com.yuyakaido.android.blueprint.infra.AppPreference
import com.yuyakaido.android.blueprint.infra.TweetRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

data class Account(val twitter: TwitterSession) {

    @Inject
    lateinit var repository: TweetRepository

    @Inject
    lateinit var appPreference: AppPreference

    @Inject
    lateinit var accountPreference: AccountPreference

    @Inject
    lateinit var disposables: CompositeDisposable

    fun onLoggedIn(application: Application) {
        (application as Blueprint).component
                .newAccountComponent(AccountModule(this))
                .inject(this)
    }

    fun onLoggedOut() {
        disposables.dispose()
    }

    fun save() {
        accountPreference.save(this)
        appPreference.save(this)
    }

    fun delete() {
        accountPreference.delete()
        appPreference.delete(this)
    }

}