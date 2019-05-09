package com.yuyakaido.android.gaia.foo.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuyakaido.android.gaia.foo.domain.GetRepoUseCase
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val application: Application,
    private val getRepoUseCase: GetRepoUseCase,
    private val query: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            application = application,
            query = query,
            getRepoUseCase = getRepoUseCase
        ) as T
    }

}