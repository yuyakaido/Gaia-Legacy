package com.yuyakaido.android.gaia.core.android

import android.content.Context
import android.content.Intent

interface EnvironmentIntentResolverType {
  fun getEnvironmentActivityIntent(context: Context): Intent
}