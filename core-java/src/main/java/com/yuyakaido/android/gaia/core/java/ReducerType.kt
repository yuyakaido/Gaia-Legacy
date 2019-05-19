package com.yuyakaido.android.gaia.core.java

interface ReducerType<STATE : StateType, ACTION : ActionType> {
    fun reduce(state: STATE, action: ACTION): STATE
}