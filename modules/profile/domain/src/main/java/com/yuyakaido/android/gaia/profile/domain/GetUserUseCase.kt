package com.yuyakaido.android.gaia.profile.domain

import com.yuyakaido.android.gaia.core.java.User
import io.reactivex.Single
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
  private val repository: UserRepositoryType
) {

  fun getUser(): Single<User> {
    return repository.getUser()
  }

}