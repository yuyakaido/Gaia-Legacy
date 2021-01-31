package com.yuyakaido.android.gaia.core

data class PresentationState(
  val article: ArticleState = ArticleState(),
  val community: CommunityState = CommunityState.Initial
) {

  fun update(article: ArticleState): PresentationState {
    return copy(article = article)
  }

  fun update(community: CommunityState): PresentationState {
    return copy(community = community)
  }

}