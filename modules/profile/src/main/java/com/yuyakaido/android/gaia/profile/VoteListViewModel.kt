package com.yuyakaido.android.gaia.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.entity.Article
import com.yuyakaido.android.gaia.core.entity.Me
import com.yuyakaido.android.gaia.core.infrastructure.ArticleRepository
import com.yuyakaido.android.gaia.core.value.VoteListPage
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class VoteListViewModel @Inject constructor(
  application: Application,
  private val me: Me,
  private val page: VoteListPage,
  private val repository: ArticleRepository
) : AndroidViewModel(application) {

  val articles = MutableLiveData<List<Article>>()

  fun onBind() {
    Timber.d("repository = ${repository.hashCode()}")
    viewModelScope.launch {
      val result = repository.votedArticles(me = me, page = page)
      articles.postValue(result.entities)
    }
  }

}