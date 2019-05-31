package com.yuyakaido.android.gaia.repo.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.yuyakaido.android.gaia.core.java.Repo
import com.yuyakaido.android.gaia.repo.domain.GetRepoUseCase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepoViewModel(
    application: Application,
    private val getRepoUseCase: GetRepoUseCase
) : AndroidViewModel(application) {

    fun getRepos(query: String): Single<List<Repo>> {
        return getRepoUseCase.getRepos(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}