package com.yuyakaido.android.gaia.bar.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.yuyakaido.android.gaia.core.Repo
import io.reactivex.Single

class BarViewModel(
    application: Application,
    private val repo: Repo
) : AndroidViewModel(application) {

    fun repo(): Single<Repo> {
        return Single.just(repo)
    }

}