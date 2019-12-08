package com.yuyakaido.android.gaia.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

// This class is based on below article.
// https://satoshun.github.io/2018/12/dagger_with_viewmodel/
class ViewModelFactory<VM : ViewModel> @Inject constructor(
  private val viewModel: Provider<VM>
) : ViewModelProvider.Factory {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return viewModel.get() as T
  }

}
