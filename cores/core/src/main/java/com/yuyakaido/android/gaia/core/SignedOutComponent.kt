package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.app.SignedOutScope
import com.yuyakaido.android.gaia.core.domain.entity.Session
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPublic
import dagger.BindsInstance
import dagger.android.DispatchingAndroidInjector
import dagger.hilt.DefineComponent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@SignedOutScope
@DefineComponent(parent = SingletonComponent::class)
interface SignedOutComponent {

  @DefineComponent.Builder
  interface Builder {
    fun session(@BindsInstance session: Session): Builder
    fun build(): SignedOutComponent
  }

  @EntryPoint
  @InstallIn(SignedOutComponent::class)
  interface Injector {
    fun androidInjector(): DispatchingAndroidInjector<Any>
    @RetrofitForPublic fun retrofitForPublic(): Retrofit
  }

}