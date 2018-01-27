package com.yuyakaido.android.blueprint.infra

import com.twitter.sdk.android.core.TwitterSession
import com.yuyakaido.android.blueprint.domain.Tweet
import io.reactivex.Observable
import javax.inject.Inject

class TwitterRepository @Inject constructor(
        private val client: TwitterClient) {

    private var cache: List<Tweet>? = null

    fun getHomeTimeline(session: TwitterSession): Observable<List<Tweet>> {
        return if (cache == null) {
            client.homeTimeline(session)
                    .doOnNext { cache = it }
        } else {
            Observable.just(cache)
        }
    }

}