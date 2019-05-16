package com.yuyakaido.android.gaia.auth.ui

import android.content.Context
import android.content.Intent
import com.yuyakaido.android.gaia.android.AuthorizationIntentResolverType

class AuthorizationIntentResolver : AuthorizationIntentResolverType {

    override fun getAuthorizationIntent(context: Context): Intent {
        return LaunchAuthorizationActivity.createIntent(context)
    }

}