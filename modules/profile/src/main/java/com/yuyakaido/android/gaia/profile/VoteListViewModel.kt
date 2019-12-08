package com.yuyakaido.android.gaia.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.Article
import com.yuyakaido.android.gaia.core.Me
import com.yuyakaido.android.gaia.core.RedditAuthService
import kotlinx.coroutines.launch
import javax.inject.Inject

class VoteListViewModel @Inject constructor(
  application: Application,
  private val service: RedditAuthService
) : AndroidViewModel(application) {

  val articles = MutableLiveData<List<Article>>()

  fun onBind(me: Me, page: VoteListPage) {
    viewModelScope.launch {
      val response = service.voted(user = me.name, type = page.path)
      val entities = response.toArticlePaginationItem().entities
      articles.postValue(entities)
    }
  }

}