package com.yuyakaido.android.gaia.support

import android.app.*
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.yuyakaido.android.gaia.core.AppLifecycle
import com.yuyakaido.android.gaia.core.AppStore
import com.yuyakaido.android.gaia.core.presentation.AppNavigatorType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DebugSupportNotificationManager(
  override val application: Application,
  override val appNavigator: AppNavigatorType,
  override val appStore: AppStore
) : SupportNotificationManager {

  companion object {
    private const val CHANNEL_ID = "notification_channel_support"
    private const val CHANNEL_NAME = "Notification for debugging"
    private const val NOTIFICATION_ID = 0
  }

  private val manager = application.getSystemService<NotificationManager>()

  override fun initialize() {
    initializeNotificationChannel()
    observeProcessLifecycleEvent()
  }

  private fun initializeNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel(
        CHANNEL_ID,
        CHANNEL_NAME,
        NotificationManager.IMPORTANCE_LOW
      )
      manager?.createNotificationChannel(channel)
    }
  }

  private fun observeProcessLifecycleEvent() {
    GlobalScope.launch {
      appStore.lifecycleAsFlow()
        .collect { lifecycle ->
          when (lifecycle) {
            AppLifecycle.OnResume -> notifyNotification()
            AppLifecycle.OnStop -> cancelNotification()
            else -> Unit
          }
        }
    }
  }

  private fun notifyNotification() {
    val notification = NotificationCompat.Builder(application, CHANNEL_ID)
      .setSmallIcon(R.mipmap.ic_launcher)
      .setContentTitle(CHANNEL_NAME)
      .setContentIntent(
        PendingIntent.getActivity(
          application,
          0,
          appNavigator.newSessionListActivity(),
          PendingIntent.FLAG_UPDATE_CURRENT
        )
      )
      .setAutoCancel(false)
      .build()
    notification.flags = Notification.FLAG_NO_CLEAR
    manager?.notify(application.packageName, NOTIFICATION_ID, notification)
  }

  private fun cancelNotification() {
    manager?.cancel(application.packageName, NOTIFICATION_ID)
  }

}