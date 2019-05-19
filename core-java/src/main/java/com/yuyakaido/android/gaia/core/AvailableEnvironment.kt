package com.yuyakaido.android.gaia.core

data class AvailableEnvironment(
    val environments: List<Environment>
) {

    fun primary(): Environment {
        return environments.first()
    }

}