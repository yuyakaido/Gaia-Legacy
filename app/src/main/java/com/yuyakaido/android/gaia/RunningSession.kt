package com.yuyakaido.android.gaia

import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.yuyakaido.android.gaia.core.java.SerializableSession
import com.yuyakaido.android.gaia.core.java.SessionState
import com.yuyakaido.android.gaia.di.SessionComponent
import com.yuyakaido.android.gaia.ext.newSessionComponent

data class RunningSession(
    private val components: MutableMap<Long, SessionComponent> = mutableMapOf()
) {

    companion object {
        private const val SESSIONS = "sessions"
    }

    fun hasSession(): Boolean {
        return components.isNotEmpty()
    }

    fun add(session: SessionState, component: SessionComponent) {
        components[session.id] = component
    }

    fun remove(session: SessionState) {
        components.remove(session.id)
    }

    fun replace(session: SessionState, component: SessionComponent) {
        components[session.id] = component
    }

    fun get(session: SessionState): SessionComponent {
        return components.getValue(session.id)
    }

    fun save(gaia: Gaia) {
        val gson = Gson()
        val arrayArray = JsonArray()
        val state = gaia.appStore.state()
        components.keys.forEach { id ->
            val session = state.sessions.first { it.id == id }
            arrayArray.add(gson.toJsonTree(SerializableSession.from(session)))
        }

        val preference = PreferenceManager.getDefaultSharedPreferences(gaia)
        val editor = preference.edit()
        editor.putString(SESSIONS, arrayArray.toString())
        editor.apply()
    }

    fun restore(gaia: Gaia): List<SessionState> {
        val gson = Gson()
        val preference = PreferenceManager.getDefaultSharedPreferences(gaia)
        val jsonString = preference.getString(SESSIONS, JsonArray().toString())
        val jsonArray = JsonParser().parse(jsonString).asJsonArray

        val sessions = jsonArray
            .map { gson.fromJson(it.toString(), SerializableSession::class.java) }
            .map { it.toSessionState() }

        sessions.associateTo(components) { session ->
            session.id to gaia.component.newSessionComponent(session)
        }

        return sessions
    }

}