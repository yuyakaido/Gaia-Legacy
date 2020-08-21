package com.yuyakaido.android.gaia.community.list

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.AppAction
import com.yuyakaido.android.gaia.core.AppState
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.domain.entity.Community
import com.yuyakaido.android.gaia.core.domain.repository.CommunityRepositoryType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class CommunityListViewModel @Inject constructor(
  application: Application,
  private val appStore: AppStore,
  private val repository: CommunityRepositoryType
) : AndroidViewModel(application) {

  sealed class State {
    abstract val progressVisibility: Int
    abstract val communities: List<Community.Detail>

    object Initial : State() {
      override val progressVisibility: Int = View.GONE
      override val communities: List<Community.Detail> = emptyList()
    }
    object Loading : State() {
      override val progressVisibility: Int = View.VISIBLE
      override val communities: List<Community.Detail> = emptyList()
    }
    data class Ideal(
      override val progressVisibility: Int = View.GONE,
      override val communities: List<Community.Detail>
    ) : State()

    companion object {
      fun from(state: AppState.CommunityState): State {
        return when (state) {
          is AppState.CommunityState.Initial -> Initial
          is AppState.CommunityState.Loading -> Loading
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

  fun onBind() {
    viewModelScope.launch {
      appStore.dispatch(AppAction.CommunityAction.ToLoading)
      val item = repository.mine()
      appStore.dispatch(AppAction.CommunityAction.ToIdeal(communities = item.entities))
    }
  }

}