package com.yuyakaido.android.gaia.repo.infra

import com.yuyakaido.android.gaia.core.java.Repo
import com.yuyakaido.android.gaia.repo.domain.RepoRepositoryType
import io.reactivex.Single
import javax.inject.Inject

class RepoRepository @Inject constructor(
    private val client: GithubClient
) : RepoRepositoryType {

    override fun fetchRepos(query: String): Single<List<Repo>> {
        return client.search(query = query)
    }

}