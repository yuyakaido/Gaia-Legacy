package com.yuyakaido.android.gaia.foo.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuyakaido.android.gaia.foo.domain.GetRepoUseCase
import javax.inject.Inject
class FooViewModelFactory @Inject constructor(
    private val application: Application,
    private val getRepoUseCase: GetRepoUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FooViewModel(
            application = application,
            getRepoUseCase = getRepoUseCase
        ) as T
    }

}