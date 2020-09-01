package com.yuyakaido.android.gaia.core

object AppReducer : ReducerType<AppState, AppAction> {

  override fun reduce(
    state: AppState,
    action: AppAction
  ): AppState {
    return action.reduce(state)
  }

}