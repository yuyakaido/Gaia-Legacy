package com.yuyakaido.android.storybook

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History(
  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = "id")
  val id: Long = 0,
  @ColumnInfo(name = "name")
  val name: String
)