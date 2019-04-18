package com.yuyakaido.android.gaia

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepoRepository {

    private val client = GithubClient()

    fun fetchRepos(query: String): Single<List<Repo>> {
        return client.search(query = query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}