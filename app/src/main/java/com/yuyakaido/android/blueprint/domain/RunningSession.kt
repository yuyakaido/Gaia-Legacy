package com.yuyakaido.android.blueprint.domain

import javax.inject.Inject

class RunningSession @Inject constructor() {

    private val sessions = mutableListOf<Session>()
    private var current: Session? = null

    fun current(): Session? {
        return current
    }

    fun switchTo(index: Int) {
        current = sessions[index]
    }

    fun add(session: Session) {
        sessions.add(session)
        current = session
    }

    fun sessions(): List<Session> {
        return sessions
    }

}