package com.yuyakaido.android.gaia.gateway.ui

import android.content.Context
import android.content.Intent
import com.yuyakaido.android.gaia.core.android.GatewayIntentResolverType

class GatewayIntentResolver : GatewayIntentResolverType {

  override fun getGatewayActivityIntent(context: Context): Intent {
    return Intent(context, GatewayActivity::class.java)
  }

}