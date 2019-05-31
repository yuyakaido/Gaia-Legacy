package com.yuyakaido.android.gaia.profile.infra

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.Rx2Apollo
import com.yuyakaido.android.gaia.core.java.User
import com.yuyakaido.android.gaia.profile.domain.UserRepositoryType
import io.reactivex.Single
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val client: ApolloClient
) : UserRepositoryType {

    override fun getUser(): Single<User> {
        return Rx2Apollo.from(client.query(GetViewerQuery()).watcher())
            .map { response ->
                val viewer = response.data()!!.viewer()
                User(
                    login = viewer.login(),
                    avatarUrl = viewer.avatarUrl().toString()
                )
            }
            .firstOrError()
    }

}