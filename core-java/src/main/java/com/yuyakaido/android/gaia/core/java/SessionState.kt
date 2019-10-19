package com.yuyakaido.android.gaia.core.java

import kotlin.math.absoluteValue
import kotlin.random.Random

sealed class SessionState {

  abstract val id: Long
  abstract val environment: Environment
  abstract val available: AvailableEnvironment

  abstract fun isLoggedOut(): Boolean
  abstract fun isLoggedIn(): Boolean

  fun toLoggedOut(): Resolved.LoggedOut {
    return Resolved.LoggedOut(
      id = id,
      environment = environment,
      available = available
    )
  }

  fun toLoggedOut(environment: Environment): Resolved.LoggedOut {
    return Resolved.LoggedOut(
      id = id,
      environment = environment,
      available = available
    )
  }

  fun toLoggedIn(token: String): Resolved.LoggedIn {
    return Resolved.LoggedIn(
      id = id,
      environment = environment,
      available = available,
      token = token
    )
  }

  fun toLoggedIn(environment: Environment, token: String): Resolved.LoggedIn {
    return Resolved.LoggedIn(
      id = id,
      environment = environment,
      available = available,
      token = token
    )
  }

  companion object {
    fun newResolvingSession(
      environment: Environment,
      available: AvailableEnvironment
    ): Resolving {
      return Resolving(
        id = Random(System.currentTimeMillis()).nextLong().absoluteValue,
        environment = environment,
        available = available
      )
    }
  }

  data class Resolving(
    override val id: Long,
    override val environment: Environment,
    override val available: AvailableEnvironment
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
      override val environment: Environment,
      override val available: AvailableEnvironment
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
      override val available: AvailableEnvironment,
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