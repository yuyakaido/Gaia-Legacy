package com.yuyakaido.android.gaia.repo.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuyakaido.android.gaia.repo.domain.GetRepoUseCase
import javax.inject.Inject

class RepoViewModelFactory @Inject constructor(
  private val application: Application,
  private val getRepoUseCase: GetRepoUseCase
) : ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return RepoViewModel(
      application = application,
      getRepoUseCase = getRepoUseCase
    ) as T
  }

}