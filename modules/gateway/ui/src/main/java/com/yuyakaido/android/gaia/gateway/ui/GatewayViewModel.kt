package com.yuyakaido.android.gaia.gateway.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.yuyakaido.android.gaia.core.java.SessionState
import io.reactivex.subjects.PublishSubject

class GatewayViewModel(
    application: Application,
    private val session: SessionState
) : AndroidViewModel(application) {

    val startAuthorizationActivity = PublishSubject.create<Unit>()
    val startHomeActivity = PublishSubject.create<Unit>()

    fun onCreate() {
        if (session.isLoggedIn()) {
            startHomeActivity.onNext(Unit)
        } else {
            startAuthorizationActivity.onNext(Unit)
        }
    }

}