package com.yuyakaido.android.gaia.core

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject

class AppStore(
    initial: AppState = AppState(),
    reducer: AppReducer = AppReducer
) : StoreType<AppState, AppAction, AppReducer>(
    initial = initial,
    reducer = reducer
) {

    override val state = BehaviorSubject.createDefault(initial)

    init {
        AppDispatcher.on(AppAction::class.java)
            .subscribeBy { action -> state.value?.let { state.onNext(reducer.reduce(it, action)) } }
    }

    override fun observable(): Observable<AppState> {
        return state
    }

    override fun single(): Single<AppState> {
        return state.singleOrError()
    }

}