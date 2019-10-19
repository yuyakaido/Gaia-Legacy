package com.yuyakaido.android.gaia.core.android

import android.content.Context
import android.content.Intent
import com.yuyakaido.android.gaia.core.java.Repo

interface RepoDetailIntentResolverType {
  fun getRepoDetailActivityIntent(context: Context, repo: Repo): Intent
}