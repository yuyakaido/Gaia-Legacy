package com.yuyakaido.android.gaia.profile.domain

import com.yuyakaido.android.gaia.core.java.User
import io.reactivex.Single

interface UserRepositoryType {
    fun getUser(): Single<User>
}