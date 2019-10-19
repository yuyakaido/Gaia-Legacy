package com.yuyakaido.android.gaia.core.java

data class AvailableEnvironment(
  val environments: List<Environment>
) {
  fun primary(): Environment {
    return environments.first()
  }

  fun secoundary(): Environment {
    return environments.last()
  }
}