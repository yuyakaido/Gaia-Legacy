package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community

data class DomainState(
  val articles: Set<Article> = emptySet(),
  val communities: Set<Community> = emptySet()
) {

  fun findArticleById(id: Article.ID): Article {
    return articles.first { it.id == id }
  }

  fun findCommunityByName(name: Community.Name): Community {
    return communities.first { it.name() == name }
  }

  fun updateArticles(newEntities: List<Article>): DomainState {
    return copy(articles = articles.plus(newEntities))
  }

  fun updateCommunities(newEntities: List<Community>): DomainState {
    return copy(communities = communities.plus(newEntities))
  }

}