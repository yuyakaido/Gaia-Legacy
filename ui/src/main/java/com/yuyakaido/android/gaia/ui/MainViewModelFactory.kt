package com.yuyakaido.android.gaia.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuyakaido.android.gaia.domain.GetRepoUseCase
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val application: Application,
    private val getRepoUseCase: GetRepoUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            application = application,
            getRepoUseCase = getRepoUseCase
        ) as T
    }

}