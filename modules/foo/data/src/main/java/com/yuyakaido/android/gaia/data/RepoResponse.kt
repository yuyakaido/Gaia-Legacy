package com.yuyakaido.android.gaia.data

import com.squareup.moshi.Json
import com.yuyakaido.android.gaia.core.Repo

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