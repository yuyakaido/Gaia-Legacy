package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.core.domain.entity.Session
import dagger.Module
import dagger.Provides

@Module
class SignedInSessionModule(
  private val session: Session
) {

  @Provides
  fun provideSession(): Session {
    return session
  }

}