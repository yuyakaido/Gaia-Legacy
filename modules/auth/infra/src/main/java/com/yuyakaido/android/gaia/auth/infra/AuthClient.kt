package com.yuyakaido.android.gaia.auth.infra

import com.yuyakaido.android.gaia.core.java.Environment
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Inject

class AuthClient @Inject constructor(
  private val environment: Environment,
  private val service: AuthService
) {

  fun getAccessToken(code: String): Single<String> {
    return service.getAccessToken(
      clientId = environment.githubClientId,
      clientSecret = environment.githubClientSecret,
      code = code
    )
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