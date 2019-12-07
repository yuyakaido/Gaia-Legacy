package com.yuyakaido.android.gaia.core

data class EntityPaginationItem<E : EntityType>(
  val entities: List<E>,
  val before: String?,
  val after: String?
)