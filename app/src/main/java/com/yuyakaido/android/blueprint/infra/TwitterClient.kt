package com.yuyakaido.android.blueprint.infra

import com.yuyakaido.android.blueprint.domain.Tweet
import io.reactivex.Observable
import retrofit2.http.GET

interface TwitterClient {

    @GET("statuses/user_timeline.json")
    fun getUserTimeline(): Observable<List<Tweet>>

}