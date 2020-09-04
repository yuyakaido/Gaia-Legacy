package com.yuyakaido.android.gaia.community.list

import com.yuyakaido.android.gaia.core.*
import com.yuyakaido.android.gaia.core.domain.repository.CommunityRepositoryType
import javax.inject.Inject

class CommunityListActionCreator @Inject constructor(
  private val repository: CommunityRepositoryType
) {

  fun refresh(): SuspendableActionType<AppState> {
    return object : SuspendableActionType<AppState> {
      override suspend fun execute(
        selector: SelectorType<AppState>,
        dispatcher: DispatcherType<AppState>
      ): ActionType<AppState> {
        dispatcher.dispatch(AppAction.CommunityAction.ToLoading)
        val item = repository.mine()
        return AppAction.CommunityAction.ToIdeal(communities = item.entities)
      }
    }
  }

}