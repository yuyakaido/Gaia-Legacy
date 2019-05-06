package com.yuyakaido.android.gaia.core

interface ReducerType<STATE : StateType, ACTION : ActionType> {
    fun reduce(state: STATE, action: ACTION): STATE
}