package com.yuyakaido.android.gaia.community.detail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.repository.CommunityRepositoryType
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommunityDetailViewModel @Inject constructor(
  application: Application,
  private val summary: Community.Summary,
  private val repository: CommunityRepositoryType
) : BaseViewModel(application) {

  val community = MutableLiveData<Community.Detail>()

  override fun onCreate() {
    super.onCreate()
    viewModelScope.launch {
      val detail = repository.detail(community = summary)
      community.value = detail
    }
  }

  fun onSubscribe() {
    community.value?.let {
      if (it.isSubscriber) {
        viewModelScope.launch {
          community.value = repository.unsubscribe(community = it)
        }
      } else {
        viewModelScope.launch {
          community.value = repository.subscribe(community = it)
        }
      }
    }
  }

}