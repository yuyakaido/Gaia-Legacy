package com.yuyakaido.android.gaia.support

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SessionListViewModel @Inject constructor(
  application: Application,
  private val appStore: AppStore
) : BaseViewModel(application) {

  override fun onCreate() {
    super.onCreate()
    viewModelScope.launch {
      appStore.signedInAsFlow()
        .collect { Timber.v("Me = ${it.me}") }
    }
  }

}