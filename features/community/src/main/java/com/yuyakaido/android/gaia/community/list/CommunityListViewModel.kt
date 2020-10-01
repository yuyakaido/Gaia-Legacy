package com.yuyakaido.android.gaia.community.list

import android.app.Application
import android.view.View
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.AppState
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.SessionState
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import javax.inject.Inject

class CommunityListViewModel @Inject constructor(
  application: Application,
  private val appStore: AppStore,
  private val actionCreator: CommunityListActionCreator
) : BaseViewModel(application) {

  sealed class State {
    abstract val communities: List<Community.Detail>
    abstract val progressVisibility: Boolean
    abstract val retryVisibility: Int

    object Initial : State() {
      override val communities: List<Community.Detail> = emptyList()
      override val progressVisibility: Boolean = true
      override val retryVisibility: Int = View.GONE
    }
    data class Loading(
      override val communities: List<Community.Detail>,
      override val progressVisibility: Boolean = true,
      override val retryVisibility: Int = View.GONE
    ) : State()
    data class Ideal(
      override val communities: List<Community.Detail>,
      override val progressVisibility: Boolean = false,
      override val retryVisibility: Int = View.GONE
    ) : State()
    object Error : State() {
      override val communities: List<Community.Detail> = emptyList()
      override val progressVisibility: Boolean = false
      override val retryVisibility: Int = View.VISIBLE
    }

    companion object {
      fun from(state: SessionState.CommunityState): State {
        return when (state) {
          is SessionState.CommunityState.Initial -> Initial
          is SessionState.CommunityState.Loading -> Loading(communities = state.communities)
          is SessionState.CommunityState.Ideal -> Ideal(communities = state.communities)
          is SessionState.CommunityState.Error -> Error
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