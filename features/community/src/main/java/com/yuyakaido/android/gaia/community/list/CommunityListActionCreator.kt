package com.yuyakaido.android.gaia.community.list

import com.yuyakaido.android.gaia.core.AppState
import com.yuyakaido.android.gaia.core.CommunityAction
import com.yuyakaido.android.gaia.core.CommunityState
import com.yuyakaido.android.gaia.core.CompletableCommunityAction
import com.yuyakaido.android.gaia.core.domain.repository.CommunityRepositoryType
import com.yuyakaido.android.reduxkit.DispatcherType
import com.yuyakaido.android.reduxkit.SelectorType
import io.reactivex.Completable
import kotlinx.coroutines.rx2.rxCompletable
import javax.inject.Inject

class CommunityListActionCreator @Inject constructor(
  private val repository: CommunityRepositoryType
) {

  fun refresh(): CommunityAction {
    return CommunityAction.ToInitial
  }

  fun paginate(): CompletableCommunityAction {
    return object : CompletableCommunityAction {
      override fun execute(
        selector: SelectorType<CommunityState>,
        dispatcher: DispatcherType<AppState>
      ): Completable {
        val state = selector.select()
        return if (state.canPaginate()) {
          dispatcher.dispatch(
            CommunityAction.ToLoading(
              communities = state.communities
            )
          )
          rxCompletable {
            val item = repository.mine(after = state.after)
            dispatcher.dispatch(
              CommunityAction.ToIdeal(
                communities = item.entities,
                after = item.after
              )
            )
          }.doOnError {
            dispatcher.dispatch(
              CommunityAction.ToError
            )
          }
        } else {
          Completable.complete()
        }
      }
    }
  }

}