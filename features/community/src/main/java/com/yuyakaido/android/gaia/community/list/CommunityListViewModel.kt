package com.yuyakaido.android.gaia.community.list

import android.app.Application
import android.view.View
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.AppAction
import com.yuyakaido.android.gaia.core.AppState
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.repository.CommunityRepositoryType
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class CommunityListViewModel @Inject constructor(
  application: Application,
  private val appStore: AppStore,
  private val repository: CommunityRepositoryType
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
      action = { store ->
        store.dispatch(AppAction.CommunityAction.ToLoading)
        val item = repository.mine()
        store.dispatch(AppAction.CommunityAction.ToIdeal(communities = item.entities))
      },
      error = { store, _ ->
        store.dispatch(AppAction.CommunityAction.ToError)
      }
    )
  }

}