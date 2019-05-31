package com.yuyakaido.android.gaia.repo.infra

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("items") val items: List<RepoResponse>
)