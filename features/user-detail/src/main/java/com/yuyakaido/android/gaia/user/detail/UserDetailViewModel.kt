package com.yuyakaido.android.gaia.user.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(
  application: Application,
  private val source: UserDetailSource,
  private val repository: UserRepositoryType
) : AndroidViewModel(application) {

  val detail = MutableLiveData<User.Detail>()

  fun onBind() {
    Timber.d("com.yuyakaido.android.storybook.repository = ${repository.hashCode()}")
    viewModelScope.launch {
      val user = source.detail(repository = repository)
      detail.value = user
    }
  }

}