package com.yuyakaido.android.gaia.auth.ui

import android.content.Context
import android.content.Intent
import com.yuyakaido.android.gaia.core.android.AuthorizationIntentResolverType

class AuthorizationIntentResolver : AuthorizationIntentResolverType {

    override fun getAuthorizationActivityIntent(context: Context): Intent {
        return LaunchAuthorizationActivity.createIntent(context)
    }

}