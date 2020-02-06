package com.yuyakaido.android.gaia.core.domain.value

import com.yuyakaido.android.gaia.core.domain.entity.Article
import com.yuyakaido.android.gaia.core.domain.entity.PaginationEntityType

data class SearchResult(
  val article: Article
) : PaginationEntityType