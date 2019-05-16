package com.yuyakaido.android.gaia.android

import android.content.Context
import android.content.Intent

interface AuthorizationIntentResolverType {
    fun getAuthorizationIntent(context: Context): Intent
}