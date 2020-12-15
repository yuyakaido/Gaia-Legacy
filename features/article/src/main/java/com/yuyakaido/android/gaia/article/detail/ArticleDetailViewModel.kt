package com.yuyakaido.android.gaia.article.detail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.repository.VoteRepositoryType
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ArticleDetailViewModel @Inject constructor(
  application: Application,
  initialArticle: Article,
  private val articleRepository: ArticleRepositoryType,
  private val voteRepository: VoteRepositoryType
) : BaseViewModel(application) {

  val article = MutableLiveData(initialArticle)

  override fun onCreate() {
    super.onCreate()
    Timber.v("ArticleRepository = $articleRepository")
  }

  fun onUpvote(target: Article) {
    viewModelScope.launch {
      article.value = voteRepository.upvote(target)
    }
  }

  fun onDownvote(target: Article) {
    viewModelScope.launch {
      article.value = voteRepository.downvote(target)
    }
  }

}