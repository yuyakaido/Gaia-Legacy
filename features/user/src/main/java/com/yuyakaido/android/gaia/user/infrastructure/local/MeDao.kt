package com.yuyakaido.android.gaia.user.infrastructure.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MeDao {

  @Query(value = "SELECT * FROM MeSchema")
  suspend fun findAll(): List<MeSchema>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(me: MeSchema)

}