package com.yuyakaido.android.gaia.community.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.infrastructure.RedditAuthApi
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class CommunityDetailViewModel @Inject constructor(
  application: Application,
  private val summary: Community.Summary,
  private val api: RedditAuthApi
) : AndroidViewModel(application) {

  fun onBind() {
    viewModelScope.launch {
      val detail = api.about(path = summary.name).toEntity()
      Timber.d(detail.toString())
    }
  }

}