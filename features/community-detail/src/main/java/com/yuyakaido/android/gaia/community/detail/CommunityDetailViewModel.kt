package com.yuyakaido.android.gaia.community.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.community.CommunityRepository
import com.yuyakaido.android.gaia.core.domain.entity.Community
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommunityDetailViewModel @Inject constructor(
  application: Application,
  private val summary: Community.Summary,
  private val repository: CommunityRepository
) : AndroidViewModel(application) {

  val community = MutableLiveData<Community.Detail>()

  fun onBind() {
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