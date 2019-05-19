package com.yuyakaido.android.gaia.di

import android.app.Application
import com.yuyakaido.android.gaia.BuildConfig
import com.yuyakaido.android.gaia.auth.ui.AuthorizationIntentResolver
import com.yuyakaido.android.gaia.bar.ui.BarIntentResolver
import com.yuyakaido.android.gaia.core.android.AuthorizationIntentResolverType
import com.yuyakaido.android.gaia.core.android.BarIntentResolverType
import com.yuyakaido.android.gaia.core.android.FooIntentResolverType
import com.yuyakaido.android.gaia.core.java.AppScope
import com.yuyakaido.android.gaia.core.java.AppStore
import com.yuyakaido.android.gaia.core.java.AvailableEnvironment
import com.yuyakaido.android.gaia.core.java.Environment
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
    fun provideAvailableEnvironment(): AvailableEnvironment {
        return AvailableEnvironment(
            environments = listOf(
                Environment(
                    githubApiEndpoint = BuildConfig.GITHUB_API_ENDPOINT,
                    githubAuthEndpoint = BuildConfig.GITHUB_AUTH_ENDPOINT,
                    githubClientId = BuildConfig.GITHUB_CLIENT_ID,
                    githubClientSecret = BuildConfig.GITHUB_CLIENT_SECRET
                )
            )
        )
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