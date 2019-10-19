package com.yuyakaido.android.gaia

import android.app.Application
import android.content.Intent
import com.yuyakaido.android.gaia.core.android.AuthorizationIntentResolverType
import com.yuyakaido.android.gaia.core.android.EnvironmentIntentResolverType
import com.yuyakaido.android.gaia.core.android.GatewayIntentResolverType
import com.yuyakaido.android.gaia.core.android.HomeIntentResolverType
import javax.inject.Inject

class AppRouter @Inject constructor(
  private val application: Application,
  private val environmentIntentResolver: EnvironmentIntentResolverType,
  private val gatewayIntentResolver: GatewayIntentResolverType,
  private val authIntentResolver: AuthorizationIntentResolverType,
  private val homeIntentResolver: HomeIntentResolverType
) {

  fun navigateToEnvironment() {
    val intent = environmentIntentResolver.getEnvironmentActivityIntent(application)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    application.startActivity(intent)
  }

  fun navigateToGateway() {
    val intent = gatewayIntentResolver.getGatewayActivityIntent(application)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    application.startActivity(intent)
  }

  fun navigateToAuth() {
    val intent = authIntentResolver.getAuthorizationActivityIntent(application)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    application.startActivity(intent)
  }

  fun navigateToHome() {
    val intent = homeIntentResolver.getHomeActivityIntent(application)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    application.startActivity(intent)
  }

}