package com.yuyakaido.android.gaia.article.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticleDetailViewModel @Inject constructor(
  application: Application,
  article: Article,
  private val repository: ArticleRepositoryType
) : AndroidViewModel(application) {

  val article = MutableLiveData<Article>(article)

  fun onUpvote(article: Article) {
    vote(target = VoteTarget.forUpvote(entity = article))
  }

  fun onDownvote(article: Article) {
    vote(target = VoteTarget.forDownvote(entity = article))
  }

  private fun vote(target: VoteTarget) {
    viewModelScope.launch {
      repository.vote(target = target)
      article.value = target.article()
    }
  }

}