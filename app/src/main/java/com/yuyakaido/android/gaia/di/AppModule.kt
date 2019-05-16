package com.yuyakaido.android.gaia.di

import android.app.Application
import com.yuyakaido.android.gaia.android.AuthorizationIntentResolverType
import com.yuyakaido.android.gaia.android.BarIntentResolverType
import com.yuyakaido.android.gaia.android.FooIntentResolverType
import com.yuyakaido.android.gaia.auth.ui.AuthorizationIntentResolver
import com.yuyakaido.android.gaia.bar.ui.BarIntentResolver
import com.yuyakaido.android.gaia.core.AppScope
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.foo.ui.FooIntentResolver
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {

    @AppScope
    @Provides
    fun provideApplication(): Application {
        return application
    }

    @AppScope
    @Provides
    fun provideAppStore(): AppStore {
        return AppStore()
    }

    @AppScope
    @Provides
    fun provideFooIntentResolverType(): FooIntentResolverType {
        return FooIntentResolver()
    }

    @AppScope
    @Provides
    fun provideBarIntentResolverType(): BarIntentResolverType {
        return BarIntentResolver()
    }

    @AppScope
    @Provides
    fun provideAuthorizationType(): AuthorizationIntentResolverType {
        return AuthorizationIntentResolver()
    }

}