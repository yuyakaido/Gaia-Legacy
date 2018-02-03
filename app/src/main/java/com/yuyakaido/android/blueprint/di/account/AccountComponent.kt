package com.yuyakaido.android.blueprint.di.account

import com.yuyakaido.android.blueprint.domain.Account
import dagger.Subcomponent

@AccountScope
@Subcomponent(modules = [AccountModule::class])
interface AccountComponent {
    fun inject(account: Account)
}