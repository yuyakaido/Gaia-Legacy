package com.yuyakaido.android.gaia.community.list

import com.yuyakaido.android.gaia.core.*
import com.yuyakaido.android.gaia.core.domain.repository.CommunityRepositoryType
import io.reactivex.Single
import kotlinx.coroutines.rx2.rxSingle
import javax.inject.Inject

class CommunityListActionCreator @Inject constructor(
  private val repository: CommunityRepositoryType
) {

  fun paginate(): SingleActionType<AppState> {
    return object : SingleActionType<AppState> {
      override fun execute(
        selector: SelectorType<AppState>,
        dispatcher: DispatcherType<AppState>
      ): Single<ActionType<AppState>> {
        return if (selector.select().community.canPaginate()) {
          Single.just(Unit)
            .doOnSubscribe {
              val state = selector.select().community
              dispatcher.dispatch(
                CommunityAction.ToLoading(
                  communities = state.communities
                )
              )
            }
            .flatMap {
              rxSingle {
                val state = selector.select().community
                val item = repository.mine(after = state.after)
                CommunityAction.ToIdeal(
                  communities = item.entities,
                  after = item.after
                )
              }
            }
        } else {
          Single.just(CommunityAction.DoNothing)
        }
      }
    }
  }

}