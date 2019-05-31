package com.yuyakaido.android.gaia.repo.domain

import com.yuyakaido.android.gaia.core.java.Repo
import io.reactivex.Single

interface RepoRepositoryType {
    fun fetchRepos(query: String): Single<List<Repo>>
}