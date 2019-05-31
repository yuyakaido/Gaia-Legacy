package com.yuyakaido.android.gaia.core.android

import android.content.Context
import android.content.Intent

interface AuthorizationIntentResolverType {
    fun getAuthorizationActivityIntent(context: Context): Intent
}