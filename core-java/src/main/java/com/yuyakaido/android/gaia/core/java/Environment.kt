package com.yuyakaido.android.gaia.core.java

data class Environment(
    val title: String,
    val githubApiEndpoint: String,
    val githubGraphQlEndpoint: String,
    val githubAuthEndpoint: String,
    val githubClientId: String,
    val githubClientSecret: String
)