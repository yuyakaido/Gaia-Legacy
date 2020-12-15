package com.yuyakaido.android.gaia.user.infrastructure.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface MeDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(me: MeSchema)

}