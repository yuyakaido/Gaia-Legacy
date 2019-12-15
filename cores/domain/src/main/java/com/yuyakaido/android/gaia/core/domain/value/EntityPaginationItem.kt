package com.yuyakaido.android.gaia.core.domain.value

import com.yuyakaido.android.gaia.core.domain.entity.EntityType

data class EntityPaginationItem<E : EntityType>(
  val entities: List<E>,
  val before: String?,
  val after: String?
)