package com.yuyakaido.android.gaia

import com.squareup.moshi.Json

data class SearchResponse(
    @Json(name = "items") val items: List<RepoResponse>
)