package com.yuyakaido.android.gaia.di

import com.yuyakaido.android.gaia.auth.ui.CompleteAuthorizationActivity
import com.yuyakaido.android.gaia.auth.ui.LaunchAuthorizationActivity
import com.yuyakaido.android.gaia.gateway.ui.GatewayActivity
import com.yuyakaido.android.gaia.gateway.ui.GatewayActivityModule
import com.yuyakaido.android.gaia.home.ui.HomeActivity
import com.yuyakaido.android.gaia.repo.detail.ui.RepoDetailActivity
import com.yuyakaido.android.gaia.repo.detail.ui.RepoDetailActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SessionActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeLaunchAuthorizationActivity(): LaunchAuthorizationActivity

    @ContributesAndroidInjector
    abstract fun contributeCompleteAuthorizationActivity(): CompleteAuthorizationActivity

    @ContributesAndroidInjector(modules = [GatewayActivityModule::class])
    abstract fun contributeGatewayActivity(): GatewayActivity

    @ContributesAndroidInjector
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [RepoDetailActivityModule::class])
    abstract fun contributeRepoDetailActivity(): RepoDetailActivity

}