package com.yuyakaido.android.gaia.user

import android.app.Application
import androidx.room.Room
import com.yuyakaido.android.gaia.core.domain.entity.Session
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import com.yuyakaido.android.gaia.user.infrastructure.local.MeDatabase
import com.yuyakaido.android.gaia.user.infrastructure.remote.UserApi
import com.yuyakaido.android.gaia.user.infrastructure.repository.UserRepository
import retrofit2.Retrofit

abstract class MainUserModule {

  private fun createUserApi(retrofit: Retrofit): UserApi {
    return retrofit.create(UserApi::class.java)
  }

  private fun createMeDatabase(
    application: Application,
    session: Session
  ): MeDatabase {
    return Room
      .databaseBuilder(
        application,
        MeDatabase::class.java,
        "${MeDatabase::class.java.simpleName}_${session.id}"
      )
      .build()
  }

  fun createUserRepositoryType(
    application: Application,
    session: Session,
    retrofit: Retrofit
  ): UserRepositoryType {
    return UserRepository(
      api = createUserApi(
        retrofit = retrofit
      ),
      database = createMeDatabase(
        application = application,
        session = session
      )
    )
  }

}