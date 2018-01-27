package com.yuyakaido.android.blueprint.domain

import com.google.gson.annotations.SerializedName

data class Tweet(
        @SerializedName("id") val id: Long,
        @SerializedName("text") val text: String,
        @SerializedName("user") val user: User
)