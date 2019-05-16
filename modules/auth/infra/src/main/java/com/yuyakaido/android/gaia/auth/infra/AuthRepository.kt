package com.yuyakaido.android.gaia.auth.infra

import com.yuyakaido.android.gaia.auth.domain.AuthRepositoryType
import io.reactivex.Single
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val client: AuthClient
) : AuthRepositoryType {

    override fun getAccessToken(code: String): Single<String> {
        return client.getAccessToken(code)
    }

}