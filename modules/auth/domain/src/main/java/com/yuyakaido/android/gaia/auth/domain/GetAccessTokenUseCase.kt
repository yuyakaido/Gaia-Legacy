package com.yuyakaido.android.gaia.auth.domain

import io.reactivex.Single
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
  private val repository: AuthRepositoryType
) {

  fun getAccessToken(code: String): Single<String> {
    return repository.getAccessToken(code)
  }

}