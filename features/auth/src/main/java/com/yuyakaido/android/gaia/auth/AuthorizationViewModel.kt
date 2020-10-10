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
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
  application: Application,
  private val appStore: AppStore,
  private val intent: Intent,
  private val tokenRepository: TokenRepositoryType,
  private val userRepository: UserRepositoryType
) : BaseViewModel(application) {

  val navigateToApp = LiveEvent<Unit>()

  override fun onCreate() {
    super.onCreate()
    dispatchNavigation()
  }

  private fun dispatchNavigation() {
    viewModelScope.launch {
      val state = intent.data?.getQueryParameter("state")
      appStore.signedOutAsFlow()
        .filter { it.state == state }
        .take(1)
        .collect {
          intent.data?.let { uri ->
            uri.getQueryParameter("code")?.let { code ->
              tokenRepository.save(tokenRepository.get(code = code))
              val me = userRepository.me()
              appStore.dispatch(
                AppAction.ReplaceSession(
                  s = it.state,
                  me = me
                )
              )
              dispatchApp()
            }
          }
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