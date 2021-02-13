package com.yuyakaido.android.gaia.article.detail

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.reduxkit.ActionType

sealed class ArticleDetailAction : ActionType<ArticleDetailState, ArticleDetailState> {

  data class Update(val article: Article) : ArticleDetailAction() {
    override fun reduce(state: ArticleDetailState): ArticleDetailState {
      return ArticleDetailState(article)
    }
  }

}