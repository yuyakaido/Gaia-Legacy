package com.yuyakaido.android.gaia.repo.detail.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.yuyakaido.android.gaia.core.java.Repo
import io.reactivex.Single

class RepoDetailViewModel(
  application: Application,
  private val repo: Repo
) : AndroidViewModel(application) {

  fun repo(): Single<Repo> {
    return Single.just(repo)
  }

}