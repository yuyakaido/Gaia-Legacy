package com.yuyakaido.android.gaia.search

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuyakaido.android.gaia.core.RedditPublicService
import javax.inject.Inject

class SearchViewModelFactory @Inject constructor(
  private val application: Application,
  private val service: RedditPublicService
) : ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return SearchViewModel(
      application = application,
      service = service
    ) as T
  }

}