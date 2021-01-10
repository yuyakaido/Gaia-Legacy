package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.app.ImageLoaderType
import com.yuyakaido.android.gaia.core.domain.app.SignedInScope
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

@SignedInScope
@DefineComponent(parent = SingletonComponent::class)
interface SignedInComponent {

  @DefineComponent.Builder
  interface Builder {
    fun session(@BindsInstance session: Session): Builder
    fun build(): SignedInComponent
  }

  @EntryPoint
  @InstallIn(SignedInComponent::class)
  interface Injector {
    fun androidInjector(): DispatchingAndroidInjector<Any>
    @RetrofitForPublic fun retrofitForPublic(): Retrofit
    @RetrofitForPrivate fun retrofitForPrivate(): Retrofit
    fun imageLoader(): ImageLoaderType
  }

}