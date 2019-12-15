package com.yuyakaido.android.gaia.article.detail

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Comment
import com.yuyakaido.android.gaia.core.infrastructure.CommentRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticleDetailViewModel @Inject constructor(
  application: Application,
  private val article: Article,
  private val repository: CommentRepository
) : AndroidViewModel(application) {

  val title = MutableLiveData<String>()
  val thumbnail = MutableLiveData<Uri>()
  val comments = MutableLiveData<List<Comment>>()

  fun onBind() {
    title.postValue(article.title)
    thumbnail.postValue(article.thumbnail)

    viewModelScope.launch {
      comments.postValue(repository.comments(article = article))
    }
  }

}