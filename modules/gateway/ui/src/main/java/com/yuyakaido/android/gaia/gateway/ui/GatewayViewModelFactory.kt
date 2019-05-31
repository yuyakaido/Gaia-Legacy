package com.yuyakaido.android.gaia.gateway.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuyakaido.android.gaia.core.java.Session
import javax.inject.Inject

class GatewayViewModelFactory @Inject constructor(
    private val application: Application,
    private val session: Session
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GatewayViewModel(
            application = application,
            session = session
        ) as T
    }

}