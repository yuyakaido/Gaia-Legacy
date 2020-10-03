package com.yuyakaido.android.gaia.community.list

import com.yuyakaido.android.gaia.core.AppAction
import com.yuyakaido.android.gaia.core.AppState
import com.yuyakaido.android.gaia.core.CommunityAction
import com.yuyakaido.android.gaia.core.SingleAction
import com.yuyakaido.android.gaia.core.domain.repository.CommunityRepositoryType
import com.yuyakaido.android.reduxkit.ActionType
import com.yuyakaido.android.reduxkit.DispatcherType
import com.yuyakaido.android.reduxkit.SelectorType
import io.reactivex.Single
import kotlinx.coroutines.rx2.rxSingle
import javax.inject.Inject

class CommunityListActionCreator @Inject constructor(
  private val repository: CommunityRepositoryType
) {

  fun refresh(): AppAction {
    return CommunityAction.ToInitial
  }

  fun paginate(): SingleAction {
    return object : SingleAction {
      override fun execute(
        selector: SelectorType<AppState>,
        dispatcher: DispatcherType<AppState>
      ): Single<out ActionType<AppState>> {
        return if (selector.select().signedIn.community.canPaginate()) {
          Single.just(Unit)
            .doOnSubscribe {
              val state = selector.select().signedIn.community
              dispatcher.dispatch(
                CommunityAction.ToLoading(
                  communities = state.communities
                )
              )
            }
            .flatMap<CommunityAction> {
              rxSingle {
                val state = selector.select().signedIn.community
                val item = repository.mine(after = state.after)
                CommunityAction.ToIdeal(
                  communities = item.entities,
                  after = item.after
                )
              }
            }
            .onErrorReturnItem(CommunityAction.ToError)
        } else {
          Single.just(CommunityAction.DoNothing)
        }
      }
    }
  }

}