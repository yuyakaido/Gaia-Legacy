package com.yuyakaido.android.gaia.core.java

import kotlin.random.Random

data class Session(
    val id: Long,
    val environment: Environment,
    val token: String?
) {

    companion object {
        fun newSession(environment: Environment): Session {
            val random = Random(System.currentTimeMillis())
            return Session(
                id = Math.abs(random.nextLong()),
                environment = environment,
                token = null
            )
        }
    }

    override fun toString(): String {
        return "$id: ${environment.title}"
    }

}