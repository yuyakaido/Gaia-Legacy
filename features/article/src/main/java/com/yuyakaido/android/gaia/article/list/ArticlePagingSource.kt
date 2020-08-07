package com.yuyakaido.android.gaia.article.list

import androidx.paging.PagingSource
import com.xwray.groupie.Item
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.repository.ArticleRepositoryType
import com.yuyakaido.android.gaia.core.presentation.ArticleItem

class ArticlePagingSource(
  private val source: ArticleListSource,
  private val repository: ArticleRepositoryType
) : PagingSource<String, Item<*>>() {

  private val upvode = { _: Article -> Unit }
  private val downvote = { _: Article -> Unit }
  private val community = { _: Article -> Unit }

  override suspend fun load(
    params: LoadParams<String>
  ): LoadResult<String, Item<*>> {
    return try {
      val result = source.articles(repository = repository, after = params.key)
      LoadResult.Page(
        data = result.entities.map { article ->
          ArticleItem(
            article = article,
            upvoteListener = upvode,
            downvoteListener = downvote,
            communityListener = community
          )
        },
        prevKey = result.before,
        nextKey = result.after
      )
    } catch (e: Exception) {
      LoadResult.Error(throwable = e)
    }
  }

}