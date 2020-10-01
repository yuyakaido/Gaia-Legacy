package com.yuyakaido.android.gaia.user.presentation.detail

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yuyakaido.android.gaia.core.domain.entity.User
import com.yuyakaido.android.gaia.core.domain.repository.UserRepositoryType
import com.yuyakaido.android.gaia.core.presentation.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(
  application: Application,
  private val source: UserDetailSource,
  private val repository: UserRepositoryType
) : BaseViewModel(application) {

  sealed class State {
    abstract val contentVisibility: Int
    abstract val progressVisibility: Int
    abstract val retryVisibility: Int

    object Initial : State() {
      override val contentVisibility: Int = View.GONE
      override val progressVisibility: Int = View.VISIBLE
      override val retryVisibility: Int = View.GONE
    }
    object Loading : State() {
      override val contentVisibility: Int = View.GONE
      override val progressVisibility: Int = View.VISIBLE
      override val retryVisibility: Int = View.GONE
    }
    data class Ideal(
      val user: User.Detail
    ) : State() {
      override val contentVisibility: Int = View.VISIBLE
      override val progressVisibility: Int = View.GONE
      override val retryVisibility: Int = View.GONE
    }
    object Error : State() {
      override val contentVisibility: Int = View.GONE
      override val progressVisibility: Int = View.GONE
      override val retryVisibility: Int = View.VISIBLE
    }
  }

  val state = MutableLiveData<State>(State.Initial)

  override fun onCreate() {
    super.onCreate()
    refresh()
  }

  fun onRetry() {
    refresh()
  }

  private fun refresh() {
    viewModelScope.launch {
      state.value = State.Loading
      val user = source.detail(repository)
      state.value = State.Ideal(user)
    }.invokeOnCompletion { cause ->
      cause?.let {
        state.value = State.Error
      }
    }
  }

}