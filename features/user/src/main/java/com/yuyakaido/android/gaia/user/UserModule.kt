package com.yuyakaido.android.gaia.user

import android.app.Application
import com.yuyakaido.android.gaia.core.ComponentHandler
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(ActivityComponent::class)
@Module
class UserModule : MainUserModule() {

  @ActivityScoped
  @Provides
  fun provideUserRepositoryType(
    application: Application,
    componentHandler: ComponentHandler
  ): UserRepositoryType {
    return createUserRepositoryType(
      application = application,
      session = componentHandler.activeSession(),
      retrofit = componentHandler.activeSigningInRetrofitForPrivate()
    )
  }

}