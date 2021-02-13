package com.yuyakaido.android.gaia.article.detail

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.reduxkit.StateType

data class ArticleDetailState(
  val article: Article
) : StateType