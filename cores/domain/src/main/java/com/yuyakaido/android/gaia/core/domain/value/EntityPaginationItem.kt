package com.yuyakaido.android.gaia.core.domain.value

import com.yuyakaido.android.gaia.core.domain.entity.PaginatableType

data class EntityPaginationItem<E : PaginatableType>(
  val entities: List<E>,
  val before: String?,
  val after: String?
)