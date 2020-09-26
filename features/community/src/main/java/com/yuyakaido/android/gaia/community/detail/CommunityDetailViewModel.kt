package com.yuyakaido.android.gaia.community.detail

import android.app.Application
import android.view.View
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

  sealed class State {
    abstract val community: Community
    abstract val contentVisibility: Int
    abstract val progressVisibility: Int

    data class Initial(
      override val community: Community.Summary,
      override val contentVisibility: Int = View.GONE,
      override val progressVisibility: Int = View.VISIBLE
    ) : State()

    data class Loading(
      override val community: Community.Summary,
      override val contentVisibility: Int = View.GONE,
      override val progressVisibility: Int = View.VISIBLE
    ) : State()

    data class Ideal(
      override val community: Community.Detail,
      override val contentVisibility: Int = View.VISIBLE,
      override val progressVisibility: Int = View.GONE
    ) : State()

    data class Error(
      override val community: Community.Summary,
      override val contentVisibility: Int = View.GONE,
      override val progressVisibility: Int = View.GONE
    ) : State()
  }

  val state = MutableLiveData<State>(State.Initial(summary))

  override fun onCreate() {
    super.onCreate()
    viewModelScope.launch {
      state.value = State.Loading(summary)
      val detail = repository.detail(summary)
      state.value = State.Ideal(detail)
    }
  }

  fun onSubscribe() {
    when (val s = state.value) {
      is State.Initial,
      is State.Loading,
      is State.Error -> Unit
      is State.Ideal -> {
        if (s.community.isSubscriber) {
          viewModelScope.launch {
            state.value = State.Ideal(repository.unsubscribe(s.community))
          }
        } else {
          viewModelScope.launch {
            state.value = State.Ideal(repository.subscribe(s.community))
          }
        }
      }
    }
  }

}