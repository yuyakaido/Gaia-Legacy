package com.yuyakaido.android.gaia.community.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.repository.CommunityRepositoryType
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommunityListViewModel @Inject constructor(
  application: Application,
  private val repository: CommunityRepositoryType
) : AndroidViewModel(application) {

  val communities = MutableLiveData<List<Community.Detail>>()

  fun onBind() {
    viewModelScope.launch {
      val item = repository.mine()
      communities.value = item.entities
    }
  }

}