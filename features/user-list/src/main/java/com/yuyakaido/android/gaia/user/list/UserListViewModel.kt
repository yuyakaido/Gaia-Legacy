package com.yuyakaido.android.gaia.user.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.UserListPage
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserListViewModel @Inject constructor(
  application: Application,
  private val page: UserListPage,
  private val repository: UserRepositoryType
) : AndroidViewModel(application) {

  val users = MutableLiveData<List<User>>()

  fun onBind() {
    viewModelScope.launch {
      users.value = page.users(repository = repository)
    }
  }

}