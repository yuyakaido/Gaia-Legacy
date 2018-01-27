package com.yuyakaido.android.blueprint.domain

import android.app.Application
import com.twitter.sdk.android.core.TwitterSession
import com.yuyakaido.android.blueprint.app.Blueprint
import com.yuyakaido.android.blueprint.di.SessionModule
import com.yuyakaido.android.blueprint.infra.TwitterClient
import javax.inject.Inject

class Session(
        val twitter: TwitterSession,
        val application: Application) {

    @Inject
    lateinit var client: TwitterClient

    init {
        (application as Blueprint).component
                .newSessionComponent(SessionModule(twitter))
                .inject(this)
    }

}