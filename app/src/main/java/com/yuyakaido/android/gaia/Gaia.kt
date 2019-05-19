package com.yuyakaido.android.gaia

import android.annotation.TargetApi
import android.app.*
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.yuyakaido.android.gaia.core.java.AppDispatcher
import com.yuyakaido.android.gaia.core.java.AppSignal
import com.yuyakaido.android.gaia.core.java.AvailableEnvironment
import com.yuyakaido.android.gaia.di.*
import com.yuyakaido.android.gaia.environment.ui.SelectEnvironmentActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class Gaia : DaggerApplication() {

    @Inject
    lateinit var appComponent: AppComponent

    @Inject
    lateinit var available: AvailableEnvironment

    private lateinit var sessionComponent: SessionComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return sessionComponent.activityInjector
    }

    override fun onCreate() {
        super.onCreate()
        setupSession()
        setupNotification()

        AppDispatcher.on(AppSignal.OpenSession::class.java)
            .subscribeBy { setupSession() }
    }

    private fun setupSession() {
        sessionComponent = appComponent
            .sessionComponentBuilder()
            .sessionModule(
                SessionModule(
                    environment = available.primary()
                )
            )
            .build()
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun setupNotification() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val id = "notification"
        val name = "Notification"
        if (Build.VERSION_CODES.M <= Build.VERSION.SDK_INT) {
            val channel = manager.getNotificationChannel(id)
            if (channel == null) {
                manager.createNotificationChannel(
                    NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT)
                )
            }
        }

        val notification = NotificationCompat.Builder(this, id)
            .setContentTitle("Current environment")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentText(available.primary().title)
            .setContentIntent(
                PendingIntent.getActivity(
                    this,
                    0,
                    SelectEnvironmentActivity.createIntent(this),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .setAutoCancel(false)
            .build()
        notification.flags = Notification.FLAG_NO_CLEAR
        manager.notify(0, notification)
    }

}