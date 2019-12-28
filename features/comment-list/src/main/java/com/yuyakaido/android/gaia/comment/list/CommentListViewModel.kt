package com.yuyakaido.android.gaia.comment.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.Comment
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.CommentRepositoryType
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommentListViewModel @Inject constructor(
  application: Application,
  private val user: User,
  private val repository: CommentRepositoryType
) : AndroidViewModel(application) {

  val comments = MutableLiveData<List<Comment>>()

  fun onBind() {
    viewModelScope.launch {
      comments.value = repository.comments(user = user)
    }
  }

}