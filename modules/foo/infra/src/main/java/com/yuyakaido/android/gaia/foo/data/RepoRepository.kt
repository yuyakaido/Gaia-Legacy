package com.yuyakaido.android.gaia.foo.data

import com.yuyakaido.android.gaia.core.Repo
import com.yuyakaido.android.gaia.foo.domain.RepoRepositoryType
import io.reactivex.Single
import javax.inject.Inject

class RepoRepository @Inject constructor(
    private val client: GithubClient
) : RepoRepositoryType {

    override fun fetchRepos(query: String): Single<List<Repo>> {
        return client.search(query = query)
    }

}