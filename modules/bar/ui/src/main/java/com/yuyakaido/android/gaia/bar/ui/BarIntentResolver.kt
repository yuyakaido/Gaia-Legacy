package com.yuyakaido.android.gaia.bar.ui

import android.content.Context
import android.content.Intent
import com.yuyakaido.android.gaia.android.BarIntentResolverType
import com.yuyakaido.android.gaia.core.Repo

class BarIntentResolver : BarIntentResolverType {

    override fun getBarActivityIntent(
        context: Context,
        repo: Repo
    ): Intent {
        return BarActivity.createIntent(context, repo)
    }

}