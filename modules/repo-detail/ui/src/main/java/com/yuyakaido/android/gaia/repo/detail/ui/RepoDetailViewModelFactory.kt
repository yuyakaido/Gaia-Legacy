package com.yuyakaido.android.gaia.repo.detail.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuyakaido.android.gaia.core.java.Repo
import javax.inject.Inject

class RepoDetailViewModelFactory @Inject constructor(
  private val application: Application,
  private val repo: Repo
) : ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return RepoDetailViewModel(
      application = application,
      repo = repo
    ) as T
  }

}