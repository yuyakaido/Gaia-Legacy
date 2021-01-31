package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community

data class DomainState(
  val articles: Map<Article.ID, Article> = emptyMap(),
  val communities: Map<String, Community> = emptyMap()
) {

  fun updateArticles(newEntities: List<Article>): DomainState {
    return copy(
      articles = articles.plus(
        newEntities.map { it.id to it }
      )
    )
  }

  fun updateCommunities(newEntities: List<Community>): DomainState {
    return copy(
      communities = communities.plus(
        newEntities.map { it.name() to it }
      )
    )
  }

}