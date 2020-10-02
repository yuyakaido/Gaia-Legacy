package com.yuyakaido.android.gaia.article.detail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ArticleDetailViewModel @Inject constructor(
  application: Application,
  article: Article,
  private val repository: ArticleRepositoryType
) : BaseViewModel(application) {

  val article = MutableLiveData(article)

  override fun onCreate() {
    super.onCreate()
    Timber.v("ArticleRepository = $repository")
  }

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