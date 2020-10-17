package com.yuyakaido.android.gaia

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.AppAction
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.SessionState
import com.yuyakaido.android.gaia.core.domain.repository.SessionRepositoryType
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
  private val sessionRepository: SessionRepositoryType,
  private val userRepository: UserRepositoryType
) : BaseViewModel(application) {

  val navigateToApp = LiveEvent<Unit>()

  override fun onCreate() {
    super.onCreate()
    dispatchNavigation()
  }

  private fun dispatchNavigation() {
    viewModelScope.launch {
      appStore.signingInAsFlow()
        .take(1)
        .collect { signingIn ->
          val me = userRepository.me()
          val session = sessionRepository.get(signingIn.id)
          val state = SessionState.SignedIn(
            id = signingIn.id,
            me = me,
            token = session.token
          )
          appStore.dispatch(AppAction.ReplaceSession(state))
          dispatchApp()
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