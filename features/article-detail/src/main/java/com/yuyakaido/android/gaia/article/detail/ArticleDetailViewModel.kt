package com.yuyakaido.android.gaia.article.detail

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Comment
import javax.inject.Inject

class ArticleDetailViewModel @Inject constructor(
  application: Application,
  private val article: Article
) : AndroidViewModel(application) {

  val title = MutableLiveData<String>()
  val thumbnail = MutableLiveData<Uri>()
  val comments = MutableLiveData<List<Comment>>()

  fun onBind() {
    title.value = article.title
    thumbnail.value = article.thumbnail
  }

}