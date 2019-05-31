package com.yuyakaido.android.gaia.repo.ui

import android.content.Context
import android.content.Intent
import com.yuyakaido.android.gaia.core.android.RepoIntentResolverType

class RepoIntentResolver : RepoIntentResolverType {

    override fun getRepoActivityIntent(context: Context): Intent {
        return RepoActivity.createIntent(context)
    }

}