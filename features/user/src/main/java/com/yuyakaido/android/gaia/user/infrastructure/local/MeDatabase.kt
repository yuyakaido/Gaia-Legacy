package com.yuyakaido.android.gaia.user.infrastructure.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
  entities = [MeSchema::class],
  version = 1
)
abstract class MeDatabase : RoomDatabase() {
  abstract fun meDao(): MeDao
}