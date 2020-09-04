package com.yuyakaido.android.gaia.community.list

import com.yuyakaido.android.gaia.core.*
import com.yuyakaido.android.gaia.core.domain.repository.CommunityRepositoryType
import io.reactivex.Single
import kotlinx.coroutines.rx2.rxSingle
import javax.inject.Inject

class CommunityListActionCreator @Inject constructor(
  private val repository: CommunityRepositoryType
) {

  fun refreshAsSuspendable(): SuspendableActionType<AppState> {
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

  fun refreshAsSingle(): SingleActionType<AppState> {
    return object : SingleActionType<AppState> {
      override fun execute(
        selector: SelectorType<AppState>,
        dispatcher: DispatcherType<AppState>
      ): Single<ActionType<AppState>> {
        return Single.just(Unit)
          .doOnSubscribe { dispatcher.dispatch(AppAction.CommunityAction.ToLoading) }
          .flatMap {
            rxSingle {
              val item = repository.mine()
              AppAction.CommunityAction.ToIdeal(communities = item.entities)
            }
          }
      }
    }
  }

}