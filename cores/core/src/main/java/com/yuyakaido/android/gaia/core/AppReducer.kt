package com.yuyakaido.android.gaia.core

object AppReducer {

  fun reduce(action: AppAction, state: AppState): AppState {
    return when (action) {
      is AppAction.IncrementCount -> {
        state.copy(count = state.count.inc())
      }
    }
  }

}