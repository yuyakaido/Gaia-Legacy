package com.yuyakaido.android.gaia.core.java

import kotlin.math.absoluteValue
import kotlin.random.Random

sealed class SessionState {

    abstract val id: Long
    abstract val environment: Environment

    abstract fun isLoggedOut(): Boolean
    abstract fun isLoggedIn(): Boolean

    fun toLoggedOut(): Resolved.LoggedOut {
        return Resolved.LoggedOut(
            id = id,
            environment = environment
        )
    }

    fun toLoggedIn(token: String): Resolved.LoggedIn {
        return Resolved.LoggedIn(
            id = id,
            environment = environment,
            token = token
        )
    }

    companion object {
        fun newResolvingSession(environment: Environment): Resolving {
            return Resolving(
                id = Random(System.currentTimeMillis()).nextLong().absoluteValue,
                environment = environment
            )
        }
    }

    data class Resolving(
        override val id: Long,
        override val environment: Environment
    ) : SessionState() {
        override fun isLoggedOut(): Boolean {
            return true
        }
        override fun isLoggedIn(): Boolean {
            return false
        }
        override fun toString(): String {
            return "$id: ${environment.title}"
        }
    }

    sealed class Resolved : SessionState() {

        data class LoggedOut(
            override val id: Long,
            override val environment: Environment
        ) : Resolved() {
            override fun isLoggedOut(): Boolean {
                return true
            }
            override fun isLoggedIn(): Boolean {
                return false
            }
            override fun toString(): String {
                return "$id: ${environment.title}"
            }
        }

        data class LoggedIn(
            override val id: Long,
            override val environment: Environment,
            val token: String
        ) : Resolved() {
            override fun isLoggedOut(): Boolean {
                return false
            }
            override fun isLoggedIn(): Boolean {
                return true
            }
            override fun toString(): String {
                return "$id: ${environment.title}"
            }
        }

    }

}