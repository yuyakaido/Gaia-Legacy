package com.yuyakaido.android.blueprint.di.app

import android.app.Application
import android.preference.PreferenceManager
import com.yuyakaido.android.blueprint.domain.LoggedInAccount
import com.yuyakaido.android.blueprint.infra.AppPreference
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
    fun provideAppPreference(): AppPreference {
        return AppPreference(application, PreferenceManager.getDefaultSharedPreferences(application))
    }

    @AppScope
    @Provides
    fun provideLoggedInAccount(preference: AppPreference): LoggedInAccount {
        return LoggedInAccount(application, preference)
    }

}