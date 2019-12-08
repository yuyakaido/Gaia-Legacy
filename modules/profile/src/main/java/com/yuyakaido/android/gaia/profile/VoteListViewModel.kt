package com.yuyakaido.android.gaia.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.entity.Article
import com.yuyakaido.android.gaia.core.entity.Me
import com.yuyakaido.android.gaia.core.infrastructure.RedditAuthService
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class VoteListViewModel @Inject constructor(
  application: Application,
  private val me: Me,
  private val page: VoteListPage,
  private val service: RedditAuthService
) : AndroidViewModel(application) {

  val articles = MutableLiveData<List<Article>>()

  fun onBind() {
    Timber.d("service = ${service.hashCode()}")
    viewModelScope.launch {
      val response = service.voted(user = me.name, type = page.path)
      val entities = response.toArticlePaginationItem().entities
      articles.postValue(entities)
    }
  }

}