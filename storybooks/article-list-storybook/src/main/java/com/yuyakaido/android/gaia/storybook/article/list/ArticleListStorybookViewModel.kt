package com.yuyakaido.android.gaia.storybook.article.list

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yuyakaido.android.gaia.article.ArticleRepository
import com.yuyakaido.android.gaia.article.list.ArticleListViewModelType
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.value.ArticleListPage
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem
import com.yuyakaido.android.gaia.core.domain.value.VoteTarget
import javax.inject.Inject
import kotlin.random.Random

class ArticleListStorybookViewModel @Inject constructor(
  application: Application,
  override val page: ArticleListPage,
  override val repository: ArticleRepository
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
      onPaginate(page)
    }
  }

  override fun onPaginate(page: ArticleListPage) {
    val oldItems = items.value ?: emptyList()
    val newItems = oldItems.plus(createDummyItem())
    items.postValue(newItems)
  }

  override fun onUpvote(article: Article) {
    refresh(target = VoteTarget.forUpvote(article = article))
  }

  override fun onDownvote(article: Article) {
    refresh(target = VoteTarget.forDownvote(article = article))
  }

}