package com.yuyakaido.android.gaia.comment.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.Comment
import com.yuyakaido.android.gaia.core.domain.repository.CommentRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommentListViewModel @Inject constructor(
  application: Application,
  private val source: CommentListSource,
  private val repository: CommentRepositoryType
) : AndroidViewModel(application) {

  val comments = MutableLiveData<List<Comment>>()

  fun onBind() {
    viewModelScope.launch {
      comments.value = source.comments(repository = repository)
    }
  }

  fun onUpvote(comment: Comment) {
    vote(target = VoteTarget.forUpvote(entity = comment))
  }

  fun onDownvote(comment: Comment) {
    vote(target = VoteTarget.forDownvote(entity = comment))
  }

  private fun vote(target: VoteTarget) {
    viewModelScope.launch {
      repository.vote(target = target)
      refresh(target = target)
    }
  }

  private fun refresh(target: VoteTarget) {
    val newItems = comments
      .value
      ?.map { comment ->
        if (comment.name == target.entity.name) {
          comment.copy(likes = target.likes)
        } else {
          comment
        }
      }
    comments.value = newItems
  }

}