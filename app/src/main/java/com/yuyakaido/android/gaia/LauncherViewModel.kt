package com.yuyakaido.android.gaia

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.AppAction
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.domain.repository.SessionRepositoryType
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import com.yuyakaido.android.gaia.core.presentation.LiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

class LauncherViewModel @ViewModelInject constructor(
  application: Application,
  private val appStore: AppStore,
  private val sessionRepository: SessionRepositoryType
) : BaseViewModel(application) {

  val navigateToAuth = LiveEvent<String>()
  val navigateToGateway = LiveEvent<Unit>()

  override fun onCreate() {
    super.onCreate()
    dispatchNavigation()
  }

  private fun dispatchNavigation() {
    viewModelScope.launch {
      appStore.stateAsFlow()
        .take(1)
        .filter { it.sessions.isEmpty() }
        .collect {
          val sessions = sessionRepository.get()
          if (sessions.isEmpty()) {
            val id = System.nanoTime().toString()
            appStore.dispatch(AppAction.AddSignedOutSession(id))
            dispatchAuth()
          } else {
            sessions.forEach {
              appStore.dispatch(AppAction.AddSigningInSession(it.id))
            }
            dispatchGateway()
          }
        }
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

}