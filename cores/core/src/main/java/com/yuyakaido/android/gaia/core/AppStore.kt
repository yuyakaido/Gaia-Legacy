package com.yuyakaido.android.gaia.core

import android.app.Application
import android.widget.Toast
import com.yuyakaido.android.gaia.core.domain.entity.ArticleListSource
import com.yuyakaido.android.reduxkit.StoreType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map

class AppStore(
  private val application: Application,
  initialState: AppState
) : StoreType<AppState, AppAction>(
  initialState = initialState,
  errorHandler = { e -> Toast.makeText(application, e.toString(), Toast.LENGTH_SHORT).show() }
) {

  private fun sessionAsFlow(): Flow<SessionState> {
    return stateAsFlow()
      .filter { it.sessions.isNotEmpty() }
      .map { it.session }
  }

  fun signedOutAsFlow(): Flow<SessionState.SignedOut> {
    return sessionAsFlow().filterIsInstance()
  }

  fun signingInAsFlow(): Flow<SessionState.SigningIn> {
    return sessionAsFlow().filterIsInstance()
  }

  fun signedInAsFlow(): Flow<SessionState.SignedIn> {
    return sessionAsFlow().filterIsInstance()
  }

  fun lifecycleAsFlow(): Flow<AppLifecycle> {
    return stateAsFlow().map { it.lifecycle }
  }

  fun articleAsFlow(source: ArticleListSource): Flow<ArticleState.ArticleListState> {
    return signedInAsFlow().map { it.article.find(source) }
  }

  fun communityAsFlow(): Flow<CommunityState> {
    return signedInAsFlow().map { it.community }
  }

}