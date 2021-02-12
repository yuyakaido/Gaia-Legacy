package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.reduxkit.StateType

data class PresentationState(
  val article: ArticleState = ArticleState(),
  val community: CommunityState = CommunityState.Initial
) : StateType {

  fun update(article: ArticleState): PresentationState {
    return copy(article = article)
  }

  fun update(community: CommunityState): PresentationState {
    return copy(community = community)
  }

}