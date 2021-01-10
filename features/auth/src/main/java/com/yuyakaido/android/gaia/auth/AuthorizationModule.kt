package com.yuyakaido.android.gaia.auth

import android.content.Intent
import com.yuyakaido.android.gaia.core.SigningInComponent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

@InstallIn(SigningInComponent::class)
@Module
class AuthorizationModule {

  @Provides
  fun provideIntent(
    activity: AuthorizationActivity
  ): Intent {
    return activity.intent
  }

}