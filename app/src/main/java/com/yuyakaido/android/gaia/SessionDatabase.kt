package com.yuyakaido.android.gaia

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
  entities = [SessionSchema::class],
  version = 1
)
abstract class SessionDatabase : RoomDatabase() {
  abstract fun sessionDao(): SessionDao
}