package com.yuyakaido.android.gaia.foo.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.yuyakaido.android.gaia.core.Repo
import com.yuyakaido.android.gaia.foo.domain.GetRepoUseCase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FooViewModel(
    application: Application,
    private val query: String,
    private val getRepoUseCase: GetRepoUseCase
) : AndroidViewModel(application) {

    fun getRepos(): Single<List<Repo>> {
        return getRepoUseCase.getRepos(query = query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}