package com.yuyakaido.android.blueprint.infra

import com.yuyakaido.android.blueprint.domain.Account
import com.yuyakaido.android.blueprint.domain.Tweet
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

class TwitterClient @Inject constructor(
        private val service: TwitterService) {

    fun getHomeTimeline(account: Account): Observable<List<Tweet>> {
        return service.homeTimeline(account.twitter.userName)
    }

    interface TwitterService {
        @GET("statuses/home_timeline.json")
        fun homeTimeline(@Query("screen_name") screenName: String): Observable<List<Tweet>>
    }

}