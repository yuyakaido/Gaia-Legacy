package com.yuyakaido.android.gaia.article.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.yuyakaido.android.gaia.core.entity.Article
import com.yuyakaido.android.gaia.core.infrastructure.RedditAuthService
import com.yuyakaido.android.gaia.core.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.value.VoteResult

abstract class ArticleListViewModelType(
  application: Application
) : AndroidViewModel(application) {

  abstract val page: ArticleListPage
  abstract val service: RedditAuthService

  abstract val items: MutableLiveData<List<EntityPaginationItem<Article>>>

  abstract fun onBind()
  abstract fun onPaginate(page: ArticleListPage)
  abstract fun onUpvote(article: Article)
  abstract fun onDownvote(article: Article)

  fun refreshByVoteResult(result: VoteResult) {
    val newItems = items
      .value
      ?.map { item ->
        item.copy(
          entities = item
            .entities
            .map { entity ->
              if (entity.id == result.article.id) {
                entity.copy(likes = result.likes)
              } else {
                entity
              }
            }
        )
      }
    items.postValue(newItems)
  }

}