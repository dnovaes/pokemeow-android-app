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
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.dnovaes.pokemeow.R
import com.dnovaes.pokemeow.services.data.NotificationChannelAux
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PokemeowMessagingService: FirebaseMessagingService() {

    companion object ChannelsConstants {
        const val DEFAULT_ID = "1001"
        const val DEFAULT_NAME = "default"

        const val EVENT_RARE_CAUGHT_ID = "1002"
        const val EVENT_RARE_CAUGHT_NAME = "rareCaught"
    }

    private val channels = listOf(
        NotificationChannelAux(DEFAULT_ID, DEFAULT_NAME, IMPORTANCE_HIGH),
        NotificationChannelAux(EVENT_RARE_CAUGHT_ID, EVENT_RARE_CAUGHT_NAME, IMPORTANCE_DEFAULT, "music_box"),
    )

    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onCreate() {
        super.onCreate()
        setupChannels()
    }

    override fun onNewToken(token: String) {
        println("logd PushMessagingService) onNewToken: `$token`")
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.run {
            if (!title.isNullOrBlank() && !body.isNullOrBlank()) {
                val channelId = channelId ?: channels.first().id
                val notification = generateIntentNotification(title!!, body!!, channelId)
                showNotification(notification)
            }
        }
    }

/*
    // Customizable Views into PushNotification
    private fun getRemoteView(title: String, body: String): RemoteViews {
        val remoteViews = RemoteViews(this.packageName, R.layout.general_notification)
        remoteViews.setTextViewText(R.id.notification_title_id, title)
        remoteViews.setTextViewText(R.id.notification_description_id, body)
        return remoteViews
    }
*/

    private fun generateIntentNotification(title: String, description: String, channelId: String): Notification {
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
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setContentText(description)
            .setStyle(NotificationCompat.BigTextStyle().bigText(description))
            .setSmallIcon(com.google.android.gms.base.R.drawable.common_full_open_on_phone)
            .setColor(ContextCompat.getColor(applicationContext, R.color.teal_200))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setAutoCancel(true)
        //builder.setContent(getRemoteView(title, "$description: $channelId"))
        return builder.build()
    }

    private fun showNotification(notification: Notification) {
        val notificationId = System.currentTimeMillis().toInt()
        notificationManager.notify(notificationId, notification)
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