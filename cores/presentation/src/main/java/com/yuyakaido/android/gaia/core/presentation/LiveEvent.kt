package com.yuyakaido.android.gaia.core.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class LiveEvent<T> : MutableLiveData<T>() {

  private val observers = mutableListOf<Observer<in T>>()
  private val hasPendingValue = AtomicBoolean(false)

  override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
    observers.add(observer)
    if (!hasObservers()) {
      super.observe(owner, Observer { value ->
        if (hasPendingValue.compareAndSet(true, false)) {
          observers.forEach { o -> o.onChanged(value) }
        }
      })
    }
  }

  override fun removeObserver(observer: Observer<in T>) {
    observers.remove(observer)
    super.removeObserver(observer)
  }

  override fun removeObservers(owner: LifecycleOwner) {
    observers.clear()
    super.removeObservers(owner)
  }

  override fun setValue(value: T) {
    hasPendingValue.set(true)
    super.setValue(value)
  }

  override fun postValue(value: T) {
    hasPendingValue.set(true)
    super.postValue(value)
  }

}