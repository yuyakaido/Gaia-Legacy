package com.yuyakaido.android.gaia.foo.data

import com.squareup.moshi.Json

data class SearchResponse(
    @Json(name = "items") val items: List<RepoResponse>
)