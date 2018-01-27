package com.yuyakaido.android.blueprint.infra

import android.util.Log
import com.twitter.sdk.android.core.TwitterSession
import com.yuyakaido.android.blueprint.domain.Tweet
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

class TwitterClient @Inject constructor(
        private val service: TwitterService) {

    fun homeTimeline(session: TwitterSession): Observable<List<Tweet>> {
        return service.homeTimeline(session.userName)
                .doOnError { Log.e("Blueprint", it.toString()) }
    }

    interface TwitterService {
        @GET("statuses/home_timeline.json")
        fun homeTimeline(@Query("screen_name") screenName: String): Observable<List<Tweet>>
    }

}