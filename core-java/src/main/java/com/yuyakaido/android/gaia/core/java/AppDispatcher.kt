package com.yuyakaido.android.gaia.core.java

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object AppDispatcher {

    private val dispatcher = PublishSubject.create<EventType>()

    fun dispatch(event: EventType) {
        dispatcher.onNext(event)
    }

    fun <T : EventType> on(clazz: Class<T>): Observable<T> {
        return dispatcher.ofType(clazz)
    }

}