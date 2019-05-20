package com.yuyakaido.android.gaia

import android.annotation.TargetApi
import android.app.*
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ProcessLifecycleOwner
import com.yuyakaido.android.gaia.core.java.*
import com.yuyakaido.android.gaia.di.*
import com.yuyakaido.android.gaia.ui.SelectEnvironmentActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class Gaia : DaggerApplication() {

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var appComponent: AppComponent

    @Inject
    lateinit var runningSession: RunningSession

    @Inject
    lateinit var appStore: AppStore

    @Inject
    lateinit var available: AvailableEnvironment

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        val state = appStore.state()
        val session = state.sessions[state.index]
        return runningSession.components.getValue(session).activityInjector
    }

    override fun onCreate() {
        super.onCreate()
        setupAppLifecycleObserver()
        setupSession()
        setupNotification()
    }

    override fun onTerminate() {
        disposables.dispose()
        super.onTerminate()
    }

    private fun createSessionComponent(session: Session): SessionComponent {
        return appComponent
            .sessionComponentBuilder()
            .sessionModule(SessionModule(session))
            .build()
    }

    private fun setupAppLifecycleObserver() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())
    }

    private fun setupSession() {
        val primaryEnvironment = available.primary()
        val primarySession = Session(environment = primaryEnvironment)
        val primaryComponent = createSessionComponent(primarySession)
        runningSession.components[primarySession] = primaryComponent
        AppDispatcher.dispatch(AppAction.AddSession(primarySession))

        AppDispatcher.on(AppSignal.OpenSession::class.java)
            .subscribeBy { signal ->
                val session = signal.session
                val component = createSessionComponent(session)
                runningSession.components[session] = component
                AppDispatcher.dispatch(AppAction.AddSession(session))
            }
            .addTo(disposables)
        AppDispatcher.on(AppSignal.CloseSession::class.java)
            .subscribeBy { signal ->
                val session = signal.session
                runningSession.components.remove(session)
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

        val showNotification = fun () {
            val state = appStore.state()

            val notification = NotificationCompat.Builder(this, id)
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
            manager.notify(0, notification)
        }

        val cancelNotification = fun () {
            manager.cancel(0)
        }

        appStore.observable()
            .map { it.index }
            .distinctUntilChanged()
            .subscribeBy { showNotification() }
            .addTo(disposables)

        AppDispatcher.on(AppSignal.NotifyAppLifecycle::class.java)
            .subscribeBy { signal ->
                when (signal.lifecycle) {
                    AppLifecycle.OnStart -> showNotification()
                    AppLifecycle.OnStop -> cancelNotification()
                    else -> Unit
                }
            }
            .addTo(disposables)
    }

}