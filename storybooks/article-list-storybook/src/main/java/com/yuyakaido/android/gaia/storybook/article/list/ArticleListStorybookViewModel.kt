package com.yuyakaido.android.gaia.storybook.article.list

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yuyakaido.android.gaia.article.list.ArticleListSource
import com.yuyakaido.android.gaia.article.list.ArticleListViewModelType
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import javax.inject.Inject
import kotlin.random.Random

class ArticleListStorybookViewModel @Inject constructor(
  application: Application,
  override val source: ArticleListSource,
  override val repository: ArticleRepositoryType
  ) : ArticleListViewModelType(application) {

  override val items = MutableLiveData<List<EntityPaginationItem<Article>>>()

  private fun createDummyItem(): EntityPaginationItem<Article> {
    return EntityPaginationItem(
      entities = List(10) { Article.create(id = Random.nextLong().toString()) },
      before = null,
      after = null
    )
  }

  override fun onBind() {
    if (items.value == null) {
      onPaginate()
    }
  }

  override fun onPaginate() {
    val oldItems = items.value ?: emptyList()
    val newItems = oldItems.plus(createDummyItem())
    items.value = newItems
  }

  override fun onUpvote(article: Article) {
    refresh(target = VoteTarget.forUpvote(entity = article))
  }

  override fun onDownvote(article: Article) {
    refresh(target = VoteTarget.forDownvote(entity = article))
  }

}