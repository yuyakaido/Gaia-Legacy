package com.yuyakaido.android.gaia.user.presentation.detail

import android.app.Application
import androidx.lifecycle.asLiveData
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(
  application: Application,
  source: UserDetailSource,
  private val repository: UserRepositoryType
) : BaseViewModel(application) {

  val detail = source.detail(repository = repository).asLiveData()

  override fun onCreate() {
    super.onCreate()
    Timber.d("repository = ${repository.hashCode()}")
  }

}