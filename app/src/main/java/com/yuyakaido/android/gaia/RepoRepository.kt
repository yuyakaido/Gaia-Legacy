package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.github.infra.GithubClient
import com.yuyakaido.android.gaia.core.Repo
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RepoRepository @Inject constructor(
    private val client: GithubClient
) {

    fun fetchRepos(query: String): Single<List<Repo>> {
        return client.search(query = query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}