package com.yuyakaido.android.gaia.auth

import android.app.Application
import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.AppAction
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import com.yuyakaido.android.gaia.core.presentation.LiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
  application: Application,
  private val appStore: AppStore,
  private val intent: Intent,
  private val repository: TokenRepositoryType
) : BaseViewModel(application) {

  val navigateToHome = LiveEvent<Unit>()
  val navigateToAuth = LiveEvent<Unit>()

  override fun onCreate() {
    super.onCreate()
    setupNavigation()
    handleToken()
  }

  private fun setupNavigation() {
    viewModelScope.launch {
      appStore.hasSessionAsFlow()
        .filter { hasSession -> hasSession }
        .take(1)
        .collect { navigateToHome.postValue(Unit) }
    }
  }

  private fun handleToken() {
    viewModelScope.launch {
      val token = repository.get()
      when {
        intent.data != null -> {
          intent.data?.let { uri ->
            uri.getQueryParameter("code")?.let { code ->
              repository.save(repository.get(code = code))
              appStore.dispatch(AppAction.AddSession)
            }
          }
        }
        token.isLoggedIn() -> {
          appStore.dispatch(AppAction.AddSession)
        }
        else -> {
          navigateToAuth.postValue(Unit)
        }
      }
    }
  }

}