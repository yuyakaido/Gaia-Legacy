package com.yuyakaido.android.gaia.gateway.ui

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides

@Module
class GatewayActivityModule {

  @Provides
  fun provideGatewayViewModel(
    activity: GatewayActivity,
    factory: GatewayViewModelFactory
  ): GatewayViewModel {
    return ViewModelProviders
      .of(activity, factory)
      .get(GatewayViewModel::class.java)
  }

}