package com.yuyakaido.android.blueprint.domain

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.jakewharton.rxrelay2.BehaviorRelay
import com.twitter.sdk.android.core.TwitterAuthToken
import com.twitter.sdk.android.core.TwitterSession
import com.yuyakaido.android.blueprint.misc.Pack
import io.reactivex.Observable
import javax.inject.Inject

class RunningSession @Inject constructor(
        private val application: Application) {

    private val sessions = BehaviorRelay.createDefault(savedSessions())
    private var current = BehaviorRelay.createDefault(Pack(sessions.value.firstOrNull()))

    private fun savedSessions(): List<Session> {
        val preference = PreferenceManager.getDefaultSharedPreferences(application)
        val sessions = preference.getStringSet("sessions", hashSetOf())
        return sessions.map {
            val sessionPreference = application.getSharedPreferences(it, Context.MODE_PRIVATE)
            val token = sessionPreference.getString("token", null)
            val secret = sessionPreference.getString("secret", null)
            val userId = sessionPreference.getLong("user_id", 0L)
            val userName = sessionPreference.getString("user_name", null)
            val autoToken = TwitterAuthToken(token, secret)
            val session = TwitterSession(autoToken, userId, userName)
            return@map Session(session, application)
        }
    }

    private fun save(session: Session) {
        val sessionPreference = application.getSharedPreferences(
                session.twitter.userId.toString(),
                Context.MODE_PRIVATE)
        sessionPreference.edit()
                .apply {
                    putString("token", session.twitter.authToken.token)
                    putString("secret", session.twitter.authToken.secret)
                    putLong("user_id", session.twitter.userId)
                    putString("user_name", session.twitter.userName)
                }
                .apply()

        val preference = PreferenceManager.getDefaultSharedPreferences(application)
        val sessions = preference.getStringSet("sessions", hashSetOf())
        sessions.add(session.twitter.userId.toString())
        preference.edit().clear().apply()
        preference.edit().putStringSet("sessions", sessions).apply()
    }

    private fun clear(session: Session) {
        val sessionPreference = application.getSharedPreferences(
                session.twitter.userId.toString(),
                Context.MODE_PRIVATE)
        sessionPreference.edit().clear().apply()

        val preference = PreferenceManager.getDefaultSharedPreferences(application)
        val sessions = preference.getStringSet("sessions", hashSetOf())
        sessions.remove(session.twitter.userId.toString())
        preference.edit().clear().apply()
        preference.edit().putStringSet("sessions", sessions).apply()
    }

    fun current(): Observable<Pack<Session?>> {
        return current
    }

    fun switchTo(index: Int) {
        if (current.hasValue() && current.value.value?.twitter?.userId != sessions.value[index].twitter.userId) {
            current.accept(Pack(sessions.value[index]))
        }
    }

    fun add(session: Session) {
        sessions.value.forEach {
            if (it.twitter.userId == session.twitter.userId) {
                return
            }
        }

        save(session)
        sessions.accept(sessions.value.plus(session))
        current.accept(Pack(session))
    }

    fun remove(session: Session) {
        clear(session)
        sessions.accept(sessions.value.minus(session))
        if (sessions.value.isEmpty()) {
            current.accept(Pack(null))
        } else {
            current.accept(Pack(sessions.value.first()))
        }
    }

    fun sessions(): Observable<List<Session>> {
        return sessions
    }

}