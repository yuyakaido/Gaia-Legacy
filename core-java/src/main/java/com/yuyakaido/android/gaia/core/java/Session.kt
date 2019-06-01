package com.yuyakaido.android.gaia.core.java

import kotlin.random.Random

data class Session(
    val id: Long,
    val environment: Environment,
    val status: Status,
    val token: String?
) {

    enum class Status {
        Active,
        Inactive
    }

    companion object {
        fun newSession(environment: Environment): Session {
            val random = Random(System.currentTimeMillis())
            return Session(
                id = Math.abs(random.nextLong()),
                environment = environment,
                status = Status.Inactive,
                token = null
            )
        }
    }

    override fun toString(): String {
        return "$id: ${environment.title}"
    }

    fun isLoggedOut(): Boolean {
        return token == null
    }

    fun isLoggedIn(): Boolean {
        return isLoggedOut().not()
    }

}