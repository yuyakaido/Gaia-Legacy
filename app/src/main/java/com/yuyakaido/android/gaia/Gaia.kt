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
import dagger.android.support.DaggerApplication
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class Gaia : DaggerApplication(), CurrentSession {

    companion object {
        private const val NOTIFICATION_ID = "notification"
        private const val NOTIFICATION_NAME = "Notification"
    }

    private val disposables = CompositeDisposable()
    private val lifecycleObserver = AppLifecycleObserver()

    @Inject
    lateinit var component: AppComponent

    @Inject
    lateinit var appRouter: AppRouter

    @Inject
    lateinit var runningSession: RunningSession

    @Inject
    lateinit var appStore: AppStore

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return if (runningSession.hasSession()) {
            val state = appStore.state()
            val session = state.sessions[state.index]
            runningSession.get(session).androidInjector()
        } else {
            component.androidInjector()
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
        ProcessLifecycleOwner.get().lifecycle.removeObserver(lifecycleObserver)
        super.onTerminate()
    }

    override fun getCurrentSession(): SessionState {
        val state = appStore.state()
        return state.sessions[state.index]
    }

    private fun setupAppLifecycleObserver() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)

        appStore.observable()
            .distinctUntilChanged { state -> state.lifecycle }
            .subscribeBy { state ->
                when (state.lifecycle) {
                    AppLifecycle.OnCreate -> onAppCreate()
                    AppLifecycle.OnStop -> onAppStop()
                    else -> Unit
                }
            }
            .addTo(disposables)
    }

    private fun setupSession() {
        AppDispatcher.on(AppSignal.AddSession::class.java)
            .subscribeBy { signal ->
                val session = signal.session
                val component = component.newSessionComponent(session)
                runningSession.add(session, component)
                AppDispatcher.dispatch(AppAction.AddSession(session))
                appRouter.navigateToGateway()
            }
            .addTo(disposables)
        AppDispatcher.on(AppSignal.RemoveSession::class.java)
            .subscribeBy { signal ->
                val session = signal.session
                runningSession.remove(session)
                AppDispatcher.dispatch(AppAction.RemoveSession(session))
            }
            .addTo(disposables)
        AppDispatcher.on(AppSignal.SelectSession::class.java)
            .subscribeBy { signal ->
                val session = signal.session
                val component = component.newSessionComponent(session)
                runningSession.replace(session, component)
                AppDispatcher.dispatch(AppAction.SelectSession(session))
                appRouter.navigateToGateway()
            }
            .addTo(disposables)
        AppDispatcher.on(AppSignal.LogOutSession::class.java)
            .subscribeBy { signal ->
                val session = signal.session.toLoggedOut()
                val component = component.newSessionComponent(session)
                runningSession.replace(session, component)
                AppDispatcher.dispatch(AppAction.LogOutSession(session))
                appRouter.navigateToEnvironment()
            }
            .addTo(disposables)
        AppDispatcher.on(AppSignal.LogInSessionWithoutEnv::class.java)
            .subscribeBy { signal ->
                val session = signal.session.toLoggedIn(signal.token)
                val component = component.newSessionComponent(session)
                runningSession.replace(session, component)
                AppDispatcher.dispatch(AppAction.LogInSession(session))
                appRouter.navigateToHome()
            }
            .addTo(disposables)
        AppDispatcher.on(AppSignal.LogInSessionWithEnv::class.java)
            .subscribeBy { signal ->
                val session = signal.session.toLoggedIn(signal.env, signal.token)
                val component = component.newSessionComponent(session)
                runningSession.replace(session, component)
                AppDispatcher.dispatch(AppAction.LogInSession(session))
                appRouter.navigateToHome()
            }
            .addTo(disposables)
        AppDispatcher.on(AppSignal.ResolveEnvironment::class.java)
            .subscribeBy { signal ->
                val session = signal.session.toLoggedOut(signal.env)
                val component = component.newSessionComponent(session)
                runningSession.replace(session, component)
                AppDispatcher.dispatch(AppAction.LogInSession(session))
                AppDispatcher.dispatch(AppSignal.NavigateToAuth)
            }
            .addTo(disposables)
        AppDispatcher.on(AppSignal.NavigateToAuth::class.java)
            .subscribeBy { appRouter.navigateToAuth() }
            .addTo(disposables)
        AppDispatcher.on(AppSignal.NavigateToHome::class.java)
            .subscribeBy { appRouter.navigateToHome() }
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
            .subscribeBy { state ->
                when (state.lifecycle) {
                    AppLifecycle.OnStop -> cancelNotification()
                    else -> showNotification(state)
                }
            }
            .addTo(disposables)
    }

    private fun showNotification(state: AppState) {
        val notification = NotificationCompat.Builder(this, NOTIFICATION_ID)
            .setContentTitle("Current environment")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentText(state.sessions.getOrNull(state.index).toString())
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

    private fun onAppCreate() {
        val sessions = runningSession.restore(this)
        AppDispatcher.dispatch(AppAction.RestoreSessions(sessions))
    }

    private fun onAppStop() {
        runningSession.save(this)
    }

}