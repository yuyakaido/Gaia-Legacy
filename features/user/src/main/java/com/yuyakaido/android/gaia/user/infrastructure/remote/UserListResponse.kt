package com.yuyakaido.android.gaia.user.infrastructure.remote

import com.yuyakaido.android.gaia.core.domain.entity.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserListResponse(
  @SerialName("data") val data: Data
) {
  @Serializable
  data class Data(
    @SerialName("children") val children: List<Child>
  ) {
    @Serializable
    data class Child(
      @SerialName("id") val id: String,
      @SerialName("name") val name: String
    )
  }
  fun toUsers(): List<User> {
    return data
      .children
      .map { child ->
        User.Summary(
          id = child.id,
          name = child.name
        )
      }
  }
}