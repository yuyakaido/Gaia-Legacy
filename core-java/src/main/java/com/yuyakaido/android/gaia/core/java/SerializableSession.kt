package com.yuyakaido.android.gaia.core.java

data class SerializableSession(
    val type: Type,
    val id: Long,
    val environment: Environment,
    val token: String?
) {

    enum class Type {
        Resolving,
        ResolvedLoggedOut,
        ResolvedLoggedIn
    }

    companion object {
        fun from(state: SessionState): SerializableSession {
            return when (state) {
                is SessionState.Resolving -> {
                    SerializableSession(
                        type = Type.Resolving,
                        id = state.id,
                        environment = state.environment,
                        token = null
                    )
                }
                is SessionState.Resolved.LoggedOut -> {
                    SerializableSession(
                        type = Type.ResolvedLoggedOut,
                        id = state.id,
                        environment = state.environment,
                        token = null
                    )
                }
                is SessionState.Resolved.LoggedIn -> {
                    SerializableSession(
                        type = Type.ResolvedLoggedIn,
                        id = state.id,
                        environment = state.environment,
                        token = state.token
                    )
                }
            }
        }
    }

    fun toSessionState(): SessionState {
        return when (type) {
            Type.Resolving -> {
                SessionState.Resolving(
                    id = id,
                    environment = environment
                )
            }
            Type.ResolvedLoggedOut -> {
                SessionState.Resolved.LoggedOut(
                    id = id,
                    environment = environment
                )
            }
            Type.ResolvedLoggedIn -> {
                SessionState.Resolved.LoggedIn(
                    id = id,
                    environment = environment,
                    token = token!!
                )
            }
        }
    }

}