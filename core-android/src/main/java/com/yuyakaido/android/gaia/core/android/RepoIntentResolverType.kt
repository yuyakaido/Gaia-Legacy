package com.yuyakaido.android.gaia.core.android

import android.content.Context
import android.content.Intent

interface RepoIntentResolverType {
    fun getRepoActivityIntent(context: Context): Intent
}