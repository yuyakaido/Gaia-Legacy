package com.yuyakaido.android.gaia.user.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.UserDetailPage
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(
  application: Application,
  private val page: UserDetailPage,
  private val repository: UserRepositoryType
) : AndroidViewModel(application) {

  val detail = MutableLiveData<User.Detail>()

  fun onBind() {
    Timber.d("repository = ${repository.hashCode()}")
    viewModelScope.launch {
      detail.value = page.detail(repository = repository)
    }
  }

}