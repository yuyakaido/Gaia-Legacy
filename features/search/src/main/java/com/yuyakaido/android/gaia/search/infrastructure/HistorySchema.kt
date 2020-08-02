package com.yuyakaido.android.gaia.search.infrastructure

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yuyakaido.android.gaia.core.domain.value.SearchHistory

@Entity
data class HistorySchema(
  @PrimaryKey val name: String
) {
  fun toEntity(): SearchHistory {
    return SearchHistory(
      name = name
    )
  }
}