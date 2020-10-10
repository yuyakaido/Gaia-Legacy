package com.yuyakaido.android.gaia

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.AppAction
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import com.yuyakaido.android.gaia.core.presentation.LiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

class GatewayViewModel @Inject constructor(
  application: Application,
  private val appStore: AppStore,
  private val tokenRepository: TokenRepositoryType,
  private val userRepository: UserRepositoryType
) : BaseViewModel(application) {

  val navigateToAuth = LiveEvent<String>()
  val navigateToApp = LiveEvent<Unit>()

  override fun onCreate() {
    super.onCreate()
    dispatchNavigation()
  }

  private fun dispatchNavigation() {
    viewModelScope.launch {
      val token = tokenRepository.get()
      if (token.isLoggedIn()) {
        val me = userRepository.me()
        appStore.dispatch(AppAction.AddSignedInSession(me))
        dispatchApp()
      } else {
        val state = System.nanoTime().toString()
        appStore.dispatch(AppAction.AddSignedOutSession(state))
        dispatchAuth()
      }
    }
  }

  private fun dispatchAuth() {
    viewModelScope.launch {
      appStore.signedOutAsFlow()
        .take(1)
        .collect {
          navigateToAuth.postValue(it.state)
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