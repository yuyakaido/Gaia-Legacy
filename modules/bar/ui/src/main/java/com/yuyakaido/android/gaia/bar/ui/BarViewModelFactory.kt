package com.yuyakaido.android.gaia.bar.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuyakaido.android.gaia.core.java.Repo
import javax.inject.Inject

class BarViewModelFactory @Inject constructor(
    private val application: Application,
    private val repo: Repo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BarViewModel(
            application = application,
            repo = repo
        ) as T
    }

}