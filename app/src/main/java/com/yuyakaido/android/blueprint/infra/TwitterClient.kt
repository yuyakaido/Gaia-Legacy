package com.yuyakaido.android.blueprint.infra

import com.yuyakaido.android.blueprint.domain.Tweet
import io.reactivex.Observable
import retrofit2.http.GET
import javax.inject.Inject

class TwitterClient @Inject constructor(
        private val service: TwitterService) {

    fun getUserTimeline(): Observable<List<Tweet>> {
        return service.getUserTimeline()
    }

    interface TwitterService {
        @GET("statuses/user_timeline.json")
        fun getUserTimeline(): Observable<List<Tweet>>
    }

}