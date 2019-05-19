package com.yuyakaido.android.gaia.foo.data

import com.google.gson.annotations.SerializedName
import com.yuyakaido.android.gaia.core.java.Repo

data class RepoResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String
) {
    fun toRepo(): Repo {
        return Repo(
            id = id,
            name = name
        )
    }
}