package com.yuyakaido.android.gaia.user

import android.app.Application
import com.yuyakaido.android.gaia.core.SigningInComponent
import com.yuyakaido.android.gaia.core.domain.app.SigningInScope
import com.yuyakaido.android.gaia.core.domain.entity.Session
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPrivate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit

@InstallIn(SigningInComponent::class)
@Module
class SigningInUserModule : MainUserModule() {

  @SigningInScope
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