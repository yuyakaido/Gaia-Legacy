package com.yuyakaido.android.gaia.auth.domain

import io.reactivex.Single

interface AuthRepositoryType {
    fun getAccessToken(code: String): Single<String>
}