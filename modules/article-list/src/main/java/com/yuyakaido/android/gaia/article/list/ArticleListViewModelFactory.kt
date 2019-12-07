package com.yuyakaido.android.gaia.article.list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuyakaido.android.gaia.core.RedditAuthService
import javax.inject.Inject

class ArticleListViewModelFactory @Inject constructor(
  private val application: Application,
  private val service: RedditAuthService
) : ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return ArticleListViewModel(
      application = application,
      service = service
    ) as T
  }

}