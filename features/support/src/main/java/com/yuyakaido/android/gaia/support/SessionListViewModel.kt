package com.yuyakaido.android.gaia.support

import android.app.Application
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.AppAction
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.SessionState
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import com.yuyakaido.android.gaia.core.presentation.LiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

class SessionListViewModel @Inject constructor(
  application: Application,
  private val appStore: AppStore
) : BaseViewModel(application) {

  val state = appStore.stateAsFlow().asLiveData()

  val navigateToAuth = LiveEvent<String>()
  val navigateToGateway = LiveEvent<Unit>()
  val navigateToApp = LiveEvent<Unit>()

  fun onAddSessionClicked() {
    val id = System.nanoTime().toString()
    appStore.dispatch(AppAction.AddSignedOutSession(id))
    dispatchAuth()
  }

  fun onSessionClicked(session: SessionState) {
    appStore.dispatch(AppAction.SwitchSession(session.id))
    when (session) {
      is SessionState.SignedOut -> dispatchAuth()
      is SessionState.SigningIn -> dispatchGateway()
      is SessionState.SignedIn -> dispatchApp()
    }
  }

  private fun dispatchAuth() {
    viewModelScope.launch {
      appStore.signedOutAsFlow()
        .take(1)
        .collect {
          navigateToAuth.postValue(it.id)
        }
    }
  }

  private fun dispatchGateway() {
    viewModelScope.launch {
      appStore.signingInAsFlow()
        .take(1)
        .collect {
          navigateToGateway.postValue(Unit)
        }
    }
  }

  private fun dispatchApp() {
    viewModelScope.launch {
      appStore.signedInAsFlow()
        .take(1)
        .collect {
          navigateToApp.postValue(Unit)
        }
    }
  }

}