package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.reduxkit.SelectorType
import com.yuyakaido.android.reduxkit.SuspendableActionType

interface SuspendableArticleAction : SuspendableActionType<AppState, ArticleState> {
  override fun selector(rootSelector: SelectorType<AppState>): SelectorType<ArticleState> {
    return object : SelectorType<ArticleState> {
      override fun select(): ArticleState {
        return rootSelector.select().article
      }
    }
  }
}
