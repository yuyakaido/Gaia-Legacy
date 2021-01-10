package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.app.SigningInScope
import com.yuyakaido.android.gaia.core.domain.entity.Session
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPrivate
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPublic
import dagger.BindsInstance
import dagger.android.DispatchingAndroidInjector
import dagger.hilt.DefineComponent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@SigningInScope
@DefineComponent(parent = SingletonComponent::class)
interface SigningInComponent {

  @DefineComponent.Builder
  interface Builder {
    fun session(@BindsInstance session: Session): Builder
    fun build(): SigningInComponent
  }

  @EntryPoint
  @InstallIn(SigningInComponent::class)
  interface Injector {
    fun androidInjector(): DispatchingAndroidInjector<Any>
    @RetrofitForPublic fun retrofitForPublic(): Retrofit
    @RetrofitForPrivate fun retrofitForPrivate(): Retrofit
  }

}