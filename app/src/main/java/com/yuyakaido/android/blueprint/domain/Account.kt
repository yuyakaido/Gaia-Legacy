package com.yuyakaido.android.blueprint.domain

import android.app.Application
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

    @Inject
    lateinit var repository: TwitterRepository

    init {
        (application as Blueprint).component
                .newAccountComponent(AccountModule(this))
                .inject(this)
    }

}