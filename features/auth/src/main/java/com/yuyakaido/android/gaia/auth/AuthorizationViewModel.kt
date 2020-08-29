package com.yuyakaido.android.gaia.auth

import android.app.Application
import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import com.yuyakaido.android.gaia.core.presentation.LiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
  application: Application,
  private val intent: Intent,
  private val repository: TokenRepositoryType
) : BaseViewModel(application) {

  val navigateToHome = LiveEvent<Unit>()
  val navigateToAuth = LiveEvent<Unit>()

  override fun onCreate() {
    super.onCreate()
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