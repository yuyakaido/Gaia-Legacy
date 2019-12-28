package com.yuyakaido.android.gaia.core.domain.value

import com.yuyakaido.android.gaia.core.domain.entity.PaginationEntityType

data class EntityPaginationItem<E : PaginationEntityType>(
  val entities: List<E>,
  val before: String?,
  val after: String?
)