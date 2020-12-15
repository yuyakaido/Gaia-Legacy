package com.yuyakaido.android.gaia.comment

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.Comment
import com.yuyakaido.android.gaia.core.domain.repository.CommentRepositoryType
import com.yuyakaido.android.gaia.core.domain.repository.VoteRepositoryType
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommentListViewModel @Inject constructor(
  application: Application,
  private val source: CommentListSource,
  private val commentRepository: CommentRepositoryType,
  private val voteRepository: VoteRepositoryType
) : BaseViewModel(application) {

  val comments = MutableLiveData<List<Comment>>()

  override fun onCreate() {
    super.onCreate()
    viewModelScope.launch {
      comments.value = source.comments(repository = commentRepository)
    }
  }

  fun onUpvote(comment: Comment) {
    viewModelScope.launch {
      refresh(voteRepository.upvote(comment))
    }
  }

  fun onDownvote(comment: Comment) {
    viewModelScope.launch {
      refresh(voteRepository.downvote(comment))
    }
  }

  private fun refresh(votedComment: Comment) {
    val newItems = comments
      .value
      ?.map { comment ->
        if (comment.name == votedComment.name) {
          votedComment
        } else {
          comment
        }
      }
    comments.value = newItems
  }

}