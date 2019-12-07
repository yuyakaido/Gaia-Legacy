package com.yuyakaido.android.gaia.subreddit.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.yuyakaido.android.gaia.core.EntityPaginationItem
import com.yuyakaido.android.gaia.core.ListingDataResponse
import com.yuyakaido.android.gaia.core.RedditAuthService
import com.yuyakaido.android.gaia.core.Subreddit
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

  val items = MutableLiveData<List<EntityPaginationItem<Subreddit>>>()
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
      .subreddits(
        path = page.path,
        after = after
      )
      .enqueue(object : Callback<ListingDataResponse> {
        override fun onResponse(call: Call<ListingDataResponse>, response: Response<ListingDataResponse>) {
          response.body()?.let { body ->
            val oldItems = items.value ?: emptyList()
            val newItems = oldItems.plus(body.toSubredditPaginationItem())
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

  fun onUpvote(subreddit: Subreddit) {
    val pair: Pair<Int, Boolean?> = when {
      subreddit.likes == null -> 1 to true
      subreddit.likes == true -> 0 to null
      subreddit.likes == false -> 1 to true
      else -> 0 to null
    }
    service
      .vote(id = subreddit.name, dir = pair.first)
      .enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
          val newItems = items
            .value
            ?.map { item ->
              item.copy(
                entities = item
                  .entities
                  .map { entity ->
                    if (entity.id == subreddit.id) {
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

  fun onDownvote(subreddit: Subreddit) {
    val pair: Pair<Int, Boolean?> = when {
      subreddit.likes == null -> -1 to false
      subreddit.likes == true -> -1 to false
      subreddit.likes == false -> 0 to null
      else -> 0 to null
    }
    service.vote(id = subreddit.name, dir = pair.first)
      .enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
          val newItems = items
            .value
            ?.map { item ->
              item.copy(
                entities = item
                  .entities
                  .map { entity ->
                    if (entity.id == subreddit.id) {
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