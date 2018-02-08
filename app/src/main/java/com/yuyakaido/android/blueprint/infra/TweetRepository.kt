package com.yuyakaido.android.blueprint.infra

import com.yuyakaido.android.blueprint.di.account.AccountScope
import com.yuyakaido.android.blueprint.domain.Tweet
import io.reactivex.Observable
import javax.inject.Inject

@AccountScope
class TweetRepository @Inject constructor(
        private val client: TwitterClient) {

    private var cache: List<Tweet>? = null

    fun getUserTimeline(): Observable<List<Tweet>> {
        return if (cache == null) {
            client.getUserTimeline()
                    .doOnNext { cache = it }
        } else {
            Observable.just(cache)
        }
    }

}