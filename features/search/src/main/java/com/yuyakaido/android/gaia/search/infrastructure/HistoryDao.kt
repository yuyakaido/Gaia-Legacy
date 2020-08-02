package com.yuyakaido.android.gaia.search.infrastructure

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {
  @Query(value = "SELECT * FROM HistorySchema")
  suspend fun findAll(): List<HistorySchema>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(history: HistorySchema)
}