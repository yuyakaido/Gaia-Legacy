package com.yuyakaido.android.storybook

import android.net.Uri
import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.value.EntityPaginationItem

object DummyEntityPaginationItem {


  fun createDummyEntityPaginationItem(
    size: Int = 10,
    category: String = "title",
    community: Community.Summary = Community.Summary(name = "category"),
    username: String = "name"
  ): EntityPaginationItem<Article> {
    return EntityPaginationItem(
      entities = (0 until size ).map { index ->
        createArticle(
          id = index.toString(),
          category = category,
          community = community,
          username = username
        )
      },
      before = null,
      after = null
    )
  }

  private fun createArticle(
    id: String,
    category: String,
    community: Community.Summary,
    username: String
  ): Article {
    return Article(
      id = Article.ID(id),
      name = username,
      community = community,
      title = category,
      thumbnail = Uri.parse("https://dummyimage.com/600x400/000/fff"),
      author = "yuyakaido",
      likes = null,
      ups = 0,
      downs = 0,
      comments = 0
    )
  }

}