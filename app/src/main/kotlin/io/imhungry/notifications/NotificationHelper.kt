package io.imhungry.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import io.imhungry.R

class NotificationHelper(private val context: Context) {

    fun sendNotificationNow(
        title: String,
        message: String,
        intent: PendingIntent?,
        priority: NotificationPriority,
        notificationID: Int
    ) {
        scheduleNotification(
            getStarterNotificationBuilder(title, message, priority).setContentIntent(intent),
            notificationID
        )
    }

    fun scheduleNotification(
        notificationBuilder: NotificationCompat.Builder, notificationID: Int
    ) {
        initNotifications()
        with(NotificationManagerCompat.from(context)) {
            notify(notificationID, notificationBuilder.build())
        }
    }

    /**
     * Removing the boiler plate from building a notification
     * Not only does this take care of starting to create a notification, but it
     * also gives you the opportunity to customize the notification if desired.
     */
    fun getStarterNotificationBuilder(
        title: String,
        message: String,
        priority: NotificationPriority
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(this.context, context.getString(R.string.primary_notification_channel_id))
            .setSmallIcon(R.drawable.ic_silverware)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(priority.value)
            // Clear the notification once tapped
            .setAutoCancel(true)
    }

    /**
     * In Android API level 26+, a notification channel must be used to send a notification.
     * the NotificationChannel class is new and not in the support library
     *
     * As per: https://developer.android.com/training/notify-user/build-notification#kotlin
     * This function can be called numerous times. If the channel is already created the system takes no action.
     */
    private fun initNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                context.getString(R.string.primary_notification_channel_id),
                context.getString(R.string.primary_notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = context.getString(R.string.primary_notification_channel_description)
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
