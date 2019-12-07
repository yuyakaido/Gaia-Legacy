package com.yuyakaido.android.gaia.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.Me
import com.yuyakaido.android.gaia.core.RedditAuthService
import kotlinx.coroutines.launch
import timber.log.Timber

class ProfileViewModel(
  application: Application,
  private val service: RedditAuthService
) : AndroidViewModel(
  application
) {

  val me = MutableLiveData<Me>()

  fun onBind() {
    Timber.d("service = ${service.hashCode()}")
    viewModelScope.launch {
      val response = service.me()
      me.postValue(response.toEntity())
    }
  }

}