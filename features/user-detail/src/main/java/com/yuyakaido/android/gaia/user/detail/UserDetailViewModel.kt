package com.yuyakaido.android.gaia.user.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.CommentRepositoryType
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(
  application: Application,
  private val source: UserDetailSource,
  private val userRepository: UserRepositoryType,
  private val commentRepository: CommentRepositoryType
) : AndroidViewModel(application) {

  val detail = MutableLiveData<User.Detail>()

  fun onBind() {
    Timber.d("repository = ${userRepository.hashCode()}")
    viewModelScope.launch {
      val user = source.detail(repository = userRepository)
      detail.value = user

      val comments = commentRepository.comments(user = user)
      Timber.d(comments.toString())
    }
  }

}