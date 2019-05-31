package com.yuyakaido.android.gaia

import android.annotation.TargetApi
import android.app.*
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ProcessLifecycleOwner
import com.yuyakaido.android.gaia.core.java.*
import com.yuyakaido.android.gaia.di.AppComponent
import com.yuyakaido.android.gaia.di.AppModule
import com.yuyakaido.android.gaia.di.DaggerAppComponent
import com.yuyakaido.android.gaia.ext.newSessionComponent
import com.yuyakaido.android.gaia.ui.SelectEnvironmentActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class Gaia : DaggerApplication() {

    companion object {
        private const val NOTIFICATION_ID = "notification"
        private const val NOTIFICATION_NAME = "Notification"
    }

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var component: AppComponent

    @Inject
    lateinit var runningSession: RunningSession

    @Inject
    lateinit var appStore: AppStore

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return if (runningSession.hasSession()) {
            val state = appStore.state()
            val session = state.sessions[state.index]
            runningSession.get(session).activityInjector()
        } else {
            component.activityInjector()
        }
    }

    override fun onCreate() {
        super.onCreate()
        setupAppLifecycleObserver()
        setupSession()
        setupNotificationChannel()
        setupNotification()
    }

    override fun onTerminate() {
        disposables.dispose()
        super.onTerminate()
    }

    private fun setupAppLifecycleObserver() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())

        appStore.observable()
            .distinctUntilChanged { state -> state.lifecycle }
            .subscribeBy { state ->
                when (state.lifecycle) {
                    AppLifecycle.OnStart -> onAppStart()
                    AppLifecycle.OnStop -> onAppStop()
                    else -> Unit
                }
            }
            .addTo(disposables)
    }

    private fun setupSession() {
        AppDispatcher.on(AppSignal.OpenSession::class.java)
            .subscribeBy { signal ->
                val session = signal.session
                val component = component.newSessionComponent(session)
                runningSession.add(session, component)
                AppDispatcher.dispatch(AppAction.AddSession(session))
            }
            .addTo(disposables)
        AppDispatcher.on(AppSignal.CloseSession::class.java)
            .subscribeBy { signal ->
                val session = signal.session
                runningSession.remove(session)
                AppDispatcher.dispatch(AppAction.RemoveSession(session))
            }
            .addTo(disposables)
        AppDispatcher.on(AppSignal.SelectSession::class.java)
            .subscribeBy { signal ->
                val session = signal.session
                AppDispatcher.dispatch(AppAction.SelectSession(session))
            }
            .addTo(disposables)
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun setupNotificationChannel() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION_CODES.M <= Build.VERSION.SDK_INT) {
            val channel = manager.getNotificationChannel(NOTIFICATION_ID)
            if (channel == null) {
                manager.createNotificationChannel(
                    NotificationChannel(
                        NOTIFICATION_ID,
                        NOTIFICATION_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                )
            }
        }
    }

    private fun setupNotification() {
        appStore.observable()
            .filter { it.sessions.isNotEmpty() }
            .subscribeBy { state ->
                when (state.lifecycle) {
                    AppLifecycle.OnStart -> showNotification(state)
                    AppLifecycle.OnStop -> cancelNotification()
                    else -> Unit
                }
            }
            .addTo(disposables)
    }

    private fun showNotification(state: AppState) {
        val notification = NotificationCompat.Builder(this, NOTIFICATION_ID)
            .setContentTitle("Current environment")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentText(state.sessions[state.index].toString())
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

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, notification)
    }

    private fun cancelNotification() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(0)
    }

    private fun onAppStart() {
        val sessions = runningSession.restore(this)
        AppDispatcher.dispatch(AppAction.RestoreSessions(sessions))
    }

    private fun onAppStop() {
        runningSession.save(this)
    }

}