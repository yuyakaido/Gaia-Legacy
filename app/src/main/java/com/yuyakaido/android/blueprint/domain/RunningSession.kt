package com.yuyakaido.android.blueprint.domain

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.twitter.sdk.android.core.TwitterAuthToken
import com.twitter.sdk.android.core.TwitterSession
import javax.inject.Inject

class RunningSession @Inject constructor(
        private val application: Application) {

    private val sessions = mutableListOf<Session>()
    private var current: Session? = null

    init { sessions.addAll(savedSessions()) }

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

    fun current(): Session? {
        return current
    }

    fun switchTo(index: Int) {
        current = sessions[index]
    }

    fun add(session: Session) {
        save(session)
        sessions.add(session)
        current = session
    }

    fun sessions(): List<Session> {
        return sessions
    }

    fun contains(session: Session): Boolean {
        sessions.forEach {
            if (it.twitter.userId == session.twitter.userId) {
                return true
            }
        }
        return false
    }

}