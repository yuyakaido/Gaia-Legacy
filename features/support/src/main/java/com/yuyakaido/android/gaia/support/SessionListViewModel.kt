package com.yuyakaido.android.gaia.support

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.AppAction
import com.yuyakaido.android.gaia.core.AppStore
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

  val navigateToAuth = LiveEvent<String>()

  fun onAddSessionClicked() {
    val s = System.nanoTime().toString()
    appStore.dispatch(AppAction.AddSignedOutSession(s))
    dispatchAuth()
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

}