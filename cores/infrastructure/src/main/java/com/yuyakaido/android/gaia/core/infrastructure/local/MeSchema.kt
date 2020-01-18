package com.yuyakaido.android.gaia.core.infrastructure.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yuyakaido.android.gaia.core.domain.entity.User

@Entity
data class MeSchema(
  @PrimaryKey val id: String,
  @ColumnInfo val name: String,
  @ColumnInfo val icon: String,
  @ColumnInfo val birthday: Float,
  @ColumnInfo val karma: Int
) {
  fun toEntity(): User.Detail.Me {
    return User.Detail.Me(
      id = id,
      name = name,
      icon = icon,
      birthday = birthday,
      karma = karma
    )
  }
}