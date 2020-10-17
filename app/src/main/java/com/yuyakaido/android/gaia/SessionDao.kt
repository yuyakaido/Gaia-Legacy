package com.yuyakaido.android.gaia

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SessionDao {

  @Query(value = "SELECT * FROM SessionSchema")
  suspend fun findAll(): List<SessionSchema>

  @Query(value = "SELECT * FROM SessionSchema WHERE id = :id")
  fun findBy(id: String): SessionSchema

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(session: SessionSchema)

}