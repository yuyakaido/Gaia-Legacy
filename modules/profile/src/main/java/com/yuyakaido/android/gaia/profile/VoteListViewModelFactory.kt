package com.yuyakaido.android.gaia.profile

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuyakaido.android.gaia.core.RedditAuthService
import javax.inject.Inject

class VoteListViewModelFactory @Inject constructor(
  private val application: Application,
  private val service: RedditAuthService
) : ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return VoteListViewModel(
      application = application,
      service = service
    ) as T
  }

}