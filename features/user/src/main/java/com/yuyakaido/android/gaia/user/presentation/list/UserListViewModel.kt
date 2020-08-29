package com.yuyakaido.android.gaia.user.presentation.list

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserListViewModel @Inject constructor(
  application: Application,
  private val source: UserListSource,
  private val repository: UserRepositoryType
) : BaseViewModel(application) {

  val users = MutableLiveData<List<User>>()

  override fun onCreate() {
    super.onCreate()
    viewModelScope.launch {
      users.value = source.users(repository = repository)
    }
  }

}