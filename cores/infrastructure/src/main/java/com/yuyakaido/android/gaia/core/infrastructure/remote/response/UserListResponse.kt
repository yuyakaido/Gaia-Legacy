package com.yuyakaido.android.gaia.core.infrastructure.remote.response

import com.squareup.moshi.Json
import com.yuyakaido.android.gaia.core.domain.entity.User

data class UserListResponse(
  @Json(name = "data") val data: Data
) {
  data class Data(
    @Json(name = "children") val children: List<Child>
  ) {
    data class Child(
      @Json(name = "id") val id: String,
      @Json(name = "name") val name: String
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