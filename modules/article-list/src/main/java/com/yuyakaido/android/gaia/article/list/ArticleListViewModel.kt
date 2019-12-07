package com.yuyakaido.android.gaia.article.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.yuyakaido.android.gaia.core.EntityPaginationItem
import com.yuyakaido.android.gaia.core.ListingDataResponse
import com.yuyakaido.android.gaia.core.RedditAuthService
import com.yuyakaido.android.gaia.core.Article
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ArticleListViewModel(
  application: Application,
  private val service: RedditAuthService
) : AndroidViewModel(
  application
) {

  val items = MutableLiveData<List<EntityPaginationItem<Article>>>()
  var after: String? = null
  var isLoading: Boolean = false

  fun onBind(page: ArticleListPage) {
    Timber.d("service = ${service.hashCode()}")
    if (items.value == null) {
      onPaginate(page)
    }
  }

  fun onPaginate(page: ArticleListPage) {
    if (isLoading) {
      return
    }

    isLoading = true
    service
      .articles(
        path = page.path,
        after = after
      )
      .enqueue(object : Callback<ListingDataResponse> {
        override fun onResponse(call: Call<ListingDataResponse>, response: Response<ListingDataResponse>) {
          response.body()?.let { body ->
            val oldItems = items.value ?: emptyList()
            val newItems = oldItems.plus(body.toArticlePaginationItem())
            items.postValue(newItems)
            after = body.data.after
          }
          isLoading = false
        }
        override fun onFailure(call: Call<ListingDataResponse>, t: Throwable) {
          Timber.e(t.toString())
          isLoading = false
        }
      })
  }

  fun onUpvote(article: Article) {
    val pair: Pair<Int, Boolean?> = when {
      article.likes == null -> 1 to true
      article.likes == true -> 0 to null
      article.likes == false -> 1 to true
      else -> 0 to null
    }
    service
      .vote(id = article.name, dir = pair.first)
      .enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
          val newItems = items
            .value
            ?.map { item ->
              item.copy(
                entities = item
                  .entities
                  .map { entity ->
                    if (entity.id == article.id) {
                      entity.copy(likes = pair.second)
                    } else {
                      entity
                    }
                  }
              )
            }
          items.postValue(newItems)
        }
        override fun onFailure(call: Call<Unit>, t: Throwable) {
          Timber.e(t.toString())
        }
      })
  }

  fun onDownvote(article: Article) {
    val pair: Pair<Int, Boolean?> = when {
      article.likes == null -> -1 to false
      article.likes == true -> -1 to false
      article.likes == false -> 0 to null
      else -> 0 to null
    }
    service.vote(id = article.name, dir = pair.first)
      .enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
          val newItems = items
            .value
            ?.map { item ->
              item.copy(
                entities = item
                  .entities
                  .map { entity ->
                    if (entity.id == article.id) {
                      entity.copy(likes = pair.second)
                    } else {
                      entity
                    }
                  }
              )
            }
          items.postValue(newItems)
        }
        override fun onFailure(call: Call<Unit>, t: Throwable) {
          Timber.e(t.toString())
        }
      })
  }

}