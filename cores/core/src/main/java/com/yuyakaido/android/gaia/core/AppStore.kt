package com.yuyakaido.android.gaia.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map

class AppStore(
  initialState: AppState = AppState()
) : StoreType<AppState, AppAction>(
  initialState = initialState
) {

  private fun sessionAsFlow(): Flow<SessionState> {
    return stateAsFlow()
      .filter { it.sessions.isNotEmpty() }
      .map { it.session }
  }

  fun signedOutAsFlow(): Flow<SessionState.SignedOut> {
    return sessionAsFlow().filterIsInstance()
  }

  fun signedInAsFlow(): Flow<SessionState.SignedIn> {
    return sessionAsFlow().filterIsInstance()
  }

  fun lifecycleAsFlow(): Flow<AppLifecycle> {
    return stateAsFlow().map { it.lifecycle }
  }

  fun articleAsFlow(): Flow<SessionState.ArticleState> {
    return signedInAsFlow().map { it.article }
  }

  fun communityAsFlow(): Flow<SessionState.CommunityState> {
    return signedInAsFlow().map { it.community }
  }

}