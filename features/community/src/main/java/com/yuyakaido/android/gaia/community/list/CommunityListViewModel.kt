package com.yuyakaido.android.gaia.community.list

import android.app.Application
import android.view.View
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.*
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class CommunityListViewModel @Inject constructor(
  application: Application,
  private val appStore: AppStore,
  private val actionCreator: CommunityListActionCreator
) : BaseViewModel(application) {

  sealed class State {
    abstract val progressVisibility: Int
    abstract val retryVisibility: Int
    abstract val communities: List<Community.Detail>

    object Initial : State() {
      override val progressVisibility: Int = View.GONE
      override val retryVisibility: Int = View.GONE
      override val communities: List<Community.Detail> = emptyList()
    }
    object Loading : State() {
      override val progressVisibility: Int = View.VISIBLE
      override val retryVisibility: Int = View.GONE
      override val communities: List<Community.Detail> = emptyList()
    }
    object Error : State() {
      override val progressVisibility: Int = View.GONE
      override val retryVisibility: Int = View.VISIBLE
      override val communities: List<Community.Detail> = emptyList()
    }
    data class Ideal(
      override val progressVisibility: Int = View.GONE,
      override val retryVisibility: Int = View.GONE,
      override val communities: List<Community.Detail>
    ) : State()

    companion object {
      fun from(state: AppState.CommunityState): State {
        return when (state) {
          is AppState.CommunityState.Initial -> Initial
          is AppState.CommunityState.Loading -> Loading
          is AppState.CommunityState.Error -> Error
          is AppState.CommunityState.Ideal -> {
            Ideal(communities = state.communities)
          }
        }
      }
    }
  }

  val state = appStore.communityAsFlow()
    .asLiveData()
    .map { state -> State.from(state) }

  override fun onCreate() {
    super.onCreate()
    refresh()
  }

  fun onRetry() {
    refresh()
  }

  private fun refresh() {
    appStore.dispatch(
      scope = viewModelScope,
      action = actionCreator.refresh()
    )
  }

}