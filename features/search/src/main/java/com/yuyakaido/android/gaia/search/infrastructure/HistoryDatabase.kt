package com.yuyakaido.android.gaia.search.infrastructure

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
  entities = [HistorySchema::class],
  version = 1
)
abstract class HistoryDatabase : RoomDatabase() {
  abstract fun historyDao(): HistoryDao
}