package com.yuyakaido.android.gaia.auth

import android.app.Application
import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.AppAction
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import com.yuyakaido.android.gaia.core.presentation.LiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
  application: Application,
  private val appStore: AppStore,
  private val intent: Intent,
  private val tokenRepository: TokenRepositoryType,
  private val userRepository: UserRepositoryType
) : BaseViewModel(application) {

  val navigateToHome = LiveEvent<Unit>()
  val navigateToAuth = LiveEvent<Unit>()

  override fun onCreate() {
    super.onCreate()
    Timber.v("AppStore = $appStore")
    initialize()
  }

  private fun initialize() {
    appStore.dispatch(AppAction.ClearSession)
    viewModelScope.launch {
      appStore.stateAsFlow()
        .filter { it.sessions.isEmpty() }
        .take(1)
        .collect { setup() }
    }
  }

  private fun setup() {
    setupNavigation()
    handleToken()
  }

  private fun setupNavigation() {
    viewModelScope.launch {
      appStore.signedOutAsFlow()
        .take(1)
        .collect {
          navigateToAuth.postValue(Unit)
        }
    }
    viewModelScope.launch {
      appStore.signedInAsFlow()
        .take(1)
        .collect {
          navigateToHome.postValue(Unit)
        }
    }
  }

  private fun handleToken() {
    viewModelScope.launch {
      val token = tokenRepository.get()
      when {
        intent.data != null -> {
          intent.data?.let { uri ->
            uri.getQueryParameter("code")?.let { code ->
              tokenRepository.save(tokenRepository.get(code = code))
              val me = userRepository.me()
              appStore.dispatch(
                AppAction.AddSignedInSession(
                  me = me
                )
              )
            }
          }
        }
        token.isLoggedIn() -> {
          val me = userRepository.me()
          appStore.dispatch(
            AppAction.AddSignedInSession(
              me = me
            )
          )
        }
        else -> {
          appStore.dispatch(AppAction.AddSignedOutSession)
        }
      }
    }
  }

}