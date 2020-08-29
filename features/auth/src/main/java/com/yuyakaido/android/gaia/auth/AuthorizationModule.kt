package com.yuyakaido.android.gaia.auth

import android.content.Intent
import dagger.Module
import dagger.Provides

@Module
class AuthorizationModule {

  @Provides
  fun provideIntent(
    activity: AuthorizationActivity
  ): Intent {
    return activity.intent
  }

}