package com.dnovaes.pokemeow.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.dnovaes.pokemeow.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

data class NotificationChannelAux(
    val id: String,
    val name: String,
    val importance: Int,
    val sound: String? = null
)

val channels = listOf(
    NotificationChannelAux("1001", "default", IMPORTANCE_HIGH),
    NotificationChannelAux("1002", "newOrder", IMPORTANCE_DEFAULT, "music_box"),
    NotificationChannelAux("1003", "doorbell", IMPORTANCE_DEFAULT, "twotone_doorbell")
)

class PokemeowMessagingService: FirebaseMessagingService() {

    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onCreate() {
        super.onCreate()
        println("logd MessagingService) onCreate called")
        setupChannels()
    }

    override fun onNewToken(token: String) {
        println("logd MessagingService) onNewToken: `$token`")
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.run {
            Log.d("logd", "onMessageReceived) New push notification: ${message.notification?.title}, ${message.notification?.body}")
            if (!title.isNullOrBlank() && !body.isNullOrBlank()) {
                val channelId = this.channelId ?: channels.first().id
                val notification = generateIntentNotification(this.title!!, this.body!!, channelId)
                showNotification(notification)
            }
        }
    }

    private fun getRemoteView(title: String, body: String): RemoteViews {
        val remoteViews = RemoteViews(this.packageName, R.layout.general_notification)
        remoteViews.setTextViewText(R.id.notification_title_id, title)
        remoteViews.setTextViewText(R.id.notification_description_id, body)
        return remoteViews
    }

    private fun generateIntentNotification(title: String, body: String, channelId: String): Notification {
        //val intent = Intent(this, OtherActivity::class.java)
        val intent = packageManager.getLaunchIntentForPackage(packageName)!!
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(com.google.android.gms.base.R.drawable.common_full_open_on_phone)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
        //builder.setContent(getRemoteView(title, "$body: $channelId"))
        return builder.build()
    }

    private fun showNotification(notification: Notification) {
        println("logd showingNotification... ChannelID: ${notification.channelId}")
        notificationManager.notify(0, notification)
    }

    private fun setupChannels() {
        channels.forEach {
            val channel = NotificationChannel(
                it.id,
                it.name,
                it.importance
            )
            it.sound?.let { soundName ->
                val soundAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()
                val soundResourceId = resources.getIdentifier(soundName, "raw", packageName);
                val soundUri = Uri.parse("android.resource://${packageName}/${soundResourceId}")
                channel.setSound(soundUri, soundAttributes)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}