package com.yuyakaido.android.gaia.core.infrastructure.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
  entities = [MeSchema::class],
  version = 1
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun meDao(): MeDao
}