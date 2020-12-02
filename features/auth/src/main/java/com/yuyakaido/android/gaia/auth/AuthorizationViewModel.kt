package com.yuyakaido.android.gaia.auth

import android.app.Application
import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.AppAction
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.SessionState
import com.yuyakaido.android.gaia.core.domain.entity.Session
import com.yuyakaido.android.gaia.core.domain.repository.SessionRepositoryType
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
  private val sessionRepository: SessionRepositoryType,
  private val tokenRepository: TokenRepositoryType
) : BaseViewModel(application) {

  val navigateToGateway = LiveEvent<Unit>()

  override fun onCreate() {
    super.onCreate()
    dispatchNavigation()
  }

  private fun dispatchNavigation() {
    viewModelScope.launch {
      val id = intent.data?.getQueryParameter("state")
      appStore.signedOutAsFlow()
        .filter { it.id == id }
        .take(1)
        .collect { signedOut ->
          intent.data?.let { uri ->
            uri.getQueryParameter("code")?.let { code ->
              val token = tokenRepository.get(code)
              val session = Session.SignedIn(
                id = signedOut.id,
                token = token
              )
              sessionRepository.post(session)
              val state = SessionState.SigningIn(
                id = signedOut.id,
                token = token
              )
              appStore.dispatch(AppAction.ReplaceSession(state))
              dispatchGateway()
            }
          }
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