package com.yuyakaido.android.storybook

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(history: History)

  @Query("SELECT * FROM history ORDER BY id LIMIT 1")
  fun getLatest(): History?
}