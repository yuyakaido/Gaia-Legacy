package com.yuyakaido.android.gaia.gateway.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.yuyakaido.android.gaia.core.java.AppDispatcher
import com.yuyakaido.android.gaia.core.java.AppSignal
import com.yuyakaido.android.gaia.core.java.AvailableEnvironment
import com.yuyakaido.android.gaia.core.java.SessionState
import io.reactivex.subjects.PublishSubject

class GatewayViewModel(
    application: Application,
    private val available: AvailableEnvironment,
    private val session: SessionState
) : AndroidViewModel(application) {

    val startAuthorizationActivity = PublishSubject.create<Unit>()
    val startHomeActivity = PublishSubject.create<Unit>()

    fun onCreate() {
        when (session) {
            is SessionState.Resolving -> {
                AppDispatcher.dispatch(AppSignal.ResolveEnvironment(session, available.primary()))
            }
            is SessionState.Resolved.LoggedOut -> {
                AppDispatcher.dispatch(AppSignal.NavigateToAuth)
            }
            is SessionState.Resolved.LoggedIn -> {
                AppDispatcher.dispatch(AppSignal.NavigateToHome)
            }
        }
    }

}