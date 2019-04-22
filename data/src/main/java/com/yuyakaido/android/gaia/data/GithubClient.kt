package com.yuyakaido.android.gaia.data

import com.yuyakaido.android.gaia.core.Repo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

class GithubClient @Inject constructor(
    private val service: GithubService
) {

    fun search(query: String): Single<List<Repo>> {
        return service.search(query = query)
            .map { response -> response.items.map { item -> item.toRepo() } }
    }

    interface GithubService {
        @GET("search/repositories")
        fun search(@Query("q") query: String): Single<SearchResponse>
    }

}