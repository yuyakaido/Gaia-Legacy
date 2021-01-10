package com.yuyakaido.android.gaia.user

import android.app.Application
import com.yuyakaido.android.gaia.core.SignedInComponent
import com.yuyakaido.android.gaia.core.domain.app.SignedInScope
import com.yuyakaido.android.gaia.core.domain.entity.Session
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPrivate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit

@InstallIn(SignedInComponent::class)
@Module
class SignedInUserModule : MainUserModule() {

  @SignedInScope
  @Provides
  fun provideUserRepositoryType(
    application: Application,
    session: Session,
    @RetrofitForPrivate retrofit: Retrofit
  ): UserRepositoryType {
    return createUserRepositoryType(
      application = application,
      session = session,
      retrofit = retrofit
    )
  }

}