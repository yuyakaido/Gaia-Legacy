package com.yuyakaido.android.blueprint

import com.twitter.sdk.android.core.TwitterSession

data class TwitterAccount(
        val id: Long,
        val name: String,
        val token: String,
        val secret: String) {

    companion object {
        fun from(session: TwitterSession): TwitterAccount {
            return TwitterAccount(
                    id = session.userId,
                    name = session.userName,
                    token = session.authToken.token,
                    secret = session.authToken.secret)
        }
    }

}