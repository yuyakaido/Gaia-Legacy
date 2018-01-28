package com.yuyakaido.android.blueprint.infra

import com.yuyakaido.android.blueprint.domain.Account
import com.yuyakaido.android.blueprint.domain.Tweet
import io.reactivex.Observable
import javax.inject.Inject

class TwitterRepository @Inject constructor(
        private val client: TwitterClient) {

    private var cache: List<Tweet>? = null

    fun getHomeTimeline(account: Account): Observable<List<Tweet>> {
        return if (cache == null) {
            client.homeTimeline(account)
                    .doOnNext { cache = it }
        } else {
            Observable.just(cache)
        }
    }

}