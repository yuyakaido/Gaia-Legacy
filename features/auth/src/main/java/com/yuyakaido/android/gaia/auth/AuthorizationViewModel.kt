package com.yuyakaido.android.gaia.auth

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.app.TokenRepositoryType
import com.yuyakaido.android.gaia.core.gateway.LiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
  application: Application,
  private val repository: TokenRepositoryType
) : AndroidViewModel(application) {

  val navigateToHome = LiveEvent<Unit>()
  val navigateToAuth = LiveEvent<Unit>()

  fun onBind(intent: Intent) {
    viewModelScope.launch {
      val token = repository.get()
      when {
        intent.data != null -> {
          intent.data?.let { uri ->
            uri.getQueryParameter("code")?.let { code ->
              repository.save(repository.get(code = code))
              navigateToHome.postValue(Unit)
            }
          }
        }
        token.isLoggedIn() -> {
          navigateToHome.postValue(Unit)
        }
        else -> {
          navigateToAuth.postValue(Unit)
        }
      }
    }
  }

}