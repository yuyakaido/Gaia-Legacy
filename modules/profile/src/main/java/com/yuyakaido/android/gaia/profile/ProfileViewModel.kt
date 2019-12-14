package com.yuyakaido.android.gaia.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.entity.Me
import com.yuyakaido.android.gaia.core.infrastructure.MeRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
  application: Application,
  private val repository: MeRepository
) : AndroidViewModel(application) {

  val me = MutableLiveData<Me>()

  fun onBind() {
    Timber.d("repository = ${repository.hashCode()}")
    viewModelScope.launch {
      me.postValue(repository.me())
    }
  }

}