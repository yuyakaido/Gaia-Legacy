package com.yuyakaido.android.gaia.article.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.value.ArticleListPage
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import com.yuyakaido.android.gaia.core.infrastructure.ArticleRepository

abstract class ArticleListViewModelType(
  application: Application
) : AndroidViewModel(application) {

  abstract val page: ArticleListPage
  abstract val repository: ArticleRepository

  abstract val items: MutableLiveData<List<EntityPaginationItem<Article>>>

  abstract fun onBind()
  abstract fun onPaginate(page: ArticleListPage)
  abstract fun onUpvote(article: Article)
  abstract fun onDownvote(article: Article)

  fun refresh(target: VoteTarget) {
    val newItems = items
      .value
      ?.map { item ->
        item.copy(
          entities = item
            .entities
            .map { entity ->
              if (entity.id == target.article.id) {
                entity.copy(likes = target.likes)
              } else {
                entity
              }
            }
        )
      }
    items.postValue(newItems)
  }

}