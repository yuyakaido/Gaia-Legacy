package com.yuyakaido.android.gaia.article.detail

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.Article
import com.yuyakaido.android.gaia.core.Comment
import com.yuyakaido.android.gaia.core.ListingDataResponse
import com.yuyakaido.android.gaia.core.RedditAuthService
import kotlinx.coroutines.launch

class ArticleDetailViewModel(
  application: Application,
  private val service: RedditAuthService
) : AndroidViewModel(
  application
) {

  val title = MutableLiveData<String>()
  val thumbnail = MutableLiveData<Uri>()
  val comments = MutableLiveData<List<Comment>>()

  fun onBind(article: Article) {
    title.postValue(article.title)
    thumbnail.postValue(article.thumbnail)

    viewModelScope.launch {
      val response = service.comments()
      val responseOfComment = response.firstOrNull {
        it.data.children.any { child ->
          child.kind == ListingDataResponse.Children.Child.Kind.Comment
        }
      }
      comments.postValue(responseOfComment?.toComments() ?: emptyList())
    }
  }

}