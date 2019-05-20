package com.yuyakaido.android.gaia.core.java

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

abstract class StoreType<STATE : StateType, ACTION : ActionType, REDUCER : ReducerType<STATE, ACTION>>(
    val initial: STATE,
    val reducer: REDUCER
) {
    abstract val state: BehaviorSubject<STATE>
    abstract fun state(): STATE
    abstract fun observable(): Observable<STATE>
    abstract fun single(): Single<STATE>
}