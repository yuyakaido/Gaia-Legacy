package com.yuyakaido.android.gaia.user.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserListViewModel @Inject constructor(
  application: Application,
  private val source: UserListSource,
  private val repository: UserRepositoryType
) : AndroidViewModel(application) {

  val users = MutableLiveData<List<User>>()

  fun onBind() {
    viewModelScope.launch {
      users.value = source.users(repository = repository)
    }
  }

}