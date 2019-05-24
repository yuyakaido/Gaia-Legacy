package com.yuyakaido.android.gaia

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.yuyakaido.android.gaia.core.java.Session
import com.yuyakaido.android.gaia.di.AppComponent
import com.yuyakaido.android.gaia.di.SessionComponent
import com.yuyakaido.android.gaia.ext.newSessionComponent

data class RunningSession(
    var components: Map<Session, SessionComponent> = mutableMapOf()
) {

    companion object {
        private const val SESSIONS = "sessions"
    }

    fun hasSession(): Boolean {
        return components.isNotEmpty()
    }

    fun sessions(): List<Session> {
        return components.keys.toList()
    }

    fun add(session: Session, component: SessionComponent) {
        components = components.plus(session to component)
    }

    fun remove(session: Session) {
        components = components.minus(session)
    }

    fun save(context: Context) {
        val gson = Gson()
        val arrayArray = JsonArray()
        components.keys.forEach { arrayArray.add(gson.toJsonTree(it)) }

        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preference.edit()
        editor.putString(SESSIONS, arrayArray.toString())
        editor.apply()
    }

    fun restore(
        context: Context,
        component: AppComponent
    ) {
        val gson = Gson()
        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        val jsonString = preference.getString(SESSIONS, JsonArray().toString())
        val jsonArray = JsonParser().parse(jsonString).asJsonArray
        components = jsonArray
            .map { gson.fromJson(it.toString(), Session::class.java) }
            .associate { session -> session to component.newSessionComponent(session) }
    }

}