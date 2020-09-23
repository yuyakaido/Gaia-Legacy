package com.yuyakaido.android.gaia.community.list

import android.app.Application
import android.view.View
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.AppState
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import javax.inject.Inject

class CommunityListViewModel @Inject constructor(
  application: Application,
  private val appStore: AppStore,
  private val actionCreator: CommunityListActionCreator
) : BaseViewModel(application) {

  sealed class State {
    abstract val progressVisibility: Boolean
    abstract val retryVisibility: Int
    abstract val communities: List<Community.Detail>

    object Initial : State() {
      override val progressVisibility: Boolean = true
      override val retryVisibility: Int = View.GONE
      override val communities: List<Community.Detail> = emptyList()
    }
    data class Loading(
      override val progressVisibility: Boolean = true,
      override val retryVisibility: Int = View.GONE,
      override val communities: List<Community.Detail>
    ) : State()
    object Error : State() {
      override val progressVisibility: Boolean = false
      override val retryVisibility: Int = View.VISIBLE
      override val communities: List<Community.Detail> = emptyList()
    }
    data class Ideal(
      override val progressVisibility: Boolean = false,
      override val retryVisibility: Int = View.GONE,
      override val communities: List<Community.Detail>
    ) : State()

    companion object {
      fun from(state: AppState.CommunityState): State {
        return when (state) {
          is AppState.CommunityState.Initial -> Initial
          is AppState.CommunityState.Loading -> Loading(communities = state.communities)
          is AppState.CommunityState.Error -> Error
          is AppState.CommunityState.Ideal -> Ideal(communities = state.communities)
        }
      }
    }
  }

  val state = appStore.communityAsFlow()
    .asLiveData()
    .map { state -> State.from(state) }

  override fun onCreate() {
    super.onCreate()
    paginate()
  }

  fun onPaginate() {
    paginate()
  }

  fun onRefresh() {
    refresh()
  }

  fun onRetry() {
    refresh()
  }

  private fun paginate() {
    appStore.dispatch(
      scope = viewModelScope,
      action = actionCreator.paginate()
    )
  }

  private fun refresh() {
    appStore.dispatch(actionCreator.refresh())
    paginate()
  }

}