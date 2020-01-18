package com.yuyakaido.android.gaia.user.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import timber.log.Timber
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(
  application: Application,
  source: UserDetailSource,
  private val repository: UserRepositoryType
) : AndroidViewModel(application) {

  val detail = source.detail(repository = repository).asLiveData()

  fun onBind() {
    Timber.d("repository = ${repository.hashCode()}")
  }

}