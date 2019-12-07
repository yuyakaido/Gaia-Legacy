package com.yuyakaido.android.gaia.article.detail

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.yuyakaido.android.gaia.core.Comment
import com.yuyakaido.android.gaia.core.GaiaType
import com.yuyakaido.android.gaia.core.ListingDataResponse
import com.yuyakaido.android.gaia.core.Article
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ArticleDetailViewModel(
  application: Application
) : AndroidViewModel(
  application
) {

  private val service = getApplication<GaiaType>().redditAuthService

  val title = MutableLiveData<String>()
  val thumbnail = MutableLiveData<Uri>()
  val comments = MutableLiveData<List<Comment>>()

  fun onBind(article: Article) {
    title.postValue(article.title)
    thumbnail.postValue(article.thumbnail)
    service
      .comments()
      .enqueue(object : Callback<List<ListingDataResponse>> {
        override fun onResponse(call: Call<List<ListingDataResponse>>, response: Response<List<ListingDataResponse>>) {
          Timber.d(response.toString())
          response.body()?.let { body ->
            val responseOfComment = body.firstOrNull {
              it.data.children.any { child ->
                child.kind == ListingDataResponse.Children.Child.Kind.Comment
              }
            }
            comments.postValue(responseOfComment?.toComments() ?: emptyList())
          }
        }
        override fun onFailure(call: Call<List<ListingDataResponse>>, t: Throwable) {
          Timber.e(t.toString())
        }
      })
  }

}