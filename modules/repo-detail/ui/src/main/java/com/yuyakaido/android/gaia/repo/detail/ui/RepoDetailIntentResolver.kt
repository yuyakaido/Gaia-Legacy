package com.yuyakaido.android.gaia.repo.detail.ui

import android.content.Context
import android.content.Intent
import com.yuyakaido.android.gaia.core.android.RepoDetailIntentResolverType
import com.yuyakaido.android.gaia.core.java.Repo

class RepoDetailIntentResolver : RepoDetailIntentResolverType {

    override fun getRepoDetailActivityIntent(
        context: Context,
        repo: Repo
    ): Intent {
        return RepoDetailActivity.createIntent(
            context,
            repo
        )
    }

}