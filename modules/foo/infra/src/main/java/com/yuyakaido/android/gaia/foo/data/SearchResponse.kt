package com.yuyakaido.android.gaia.foo.data

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("items") val items: List<RepoResponse>
)