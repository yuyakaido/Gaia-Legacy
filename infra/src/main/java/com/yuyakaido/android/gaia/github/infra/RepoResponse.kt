package com.yuyakaido.android.gaia.github.infra

import com.squareup.moshi.Json

data class RepoResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String
) {
    fun toRepo(): Repo {
        return Repo(
            id = id,
            name = name
        )
    }
}