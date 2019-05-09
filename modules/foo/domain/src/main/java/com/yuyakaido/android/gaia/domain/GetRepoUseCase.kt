package com.yuyakaido.android.gaia.domain

import com.yuyakaido.android.gaia.core.Repo
import io.reactivex.Single
import javax.inject.Inject

class GetRepoUseCase @Inject constructor(
    private val repository: RepoRepositoryType
) {

    fun getRepos(query: String): Single<List<Repo>> {
        return repository.fetchRepos(query = query)
    }

}