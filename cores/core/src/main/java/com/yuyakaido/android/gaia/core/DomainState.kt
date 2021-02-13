package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.reduxkit.StateType

data class DomainState(
  val articles: Map<Article.ID, Article> = emptyMap(),
  val communities: Map<Community.Name, Community> = emptyMap()
) : StateType {

  fun findArticleById(id: Article.ID): Article {
    return articles.getValue(id)
  }

  fun findCommunityByName(name: Community.Name): Community {
    return communities.getValue(name)
  }

  fun updateArticles(newEntities: List<Article>): DomainState {
    return copy(articles = articles.plus(newEntities.map { it.id to it }))
  }

  fun updateCommunities(newEntities: List<Community>): DomainState {
    return copy(communities = communities.plus(newEntities.map { it.name() to it }))
  }

}