package com.yuyakaido.android.gaia.auth.infra

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Inject

class AuthClient @Inject constructor(
    private val service: AuthService
) {

    fun getAccessToken(code: String): Single<String> {
        return service.getAccessToken(
            clientId = BuildConfig.GITHUB_CLIENT_ID,
            clientSecret = BuildConfig.GITHUB_CLIENT_SECRET,
            code = code)
            .map { it.value }
    }

    interface AuthService {
        @FormUrlEncoded
        @Headers("Accept: application/json")
        @POST("access_token")
        fun getAccessToken(
            @Field("client_id") clientId: String,
            @Field("client_secret") clientSecret: String,
            @Field("code") code: String
        ): Single<AccessTokenResponse>
    }

}