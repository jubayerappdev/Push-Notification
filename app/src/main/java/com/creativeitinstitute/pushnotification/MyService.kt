package com.creativeitinstitute.pushnotification


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("TAG", "onNewToken: $token")


    }


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        var title = message.notification?.title
        var body = message.notification?.body


        Log.d("TAG", "title: $title")
        Log.d("TAG", "body: $body")

        showNotification(title, body)


    }

    private fun showNotification(title: String?, body: String?) {
        var channel: NotificationChannel? = null
        var builder: NotificationCompat.Builder? = null

        val channelID = "com.creativeitinstitute.pushnotification.test"

        var manager: NotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(this@MyService, NotificationActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this@MyService,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = NotificationChannel(channelID, channelID, NotificationManager.IMPORTANCE_HIGH)

            manager.createNotificationChannel(channel)

            builder = NotificationCompat.Builder(this@MyService, channelID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
        } else {
            builder = NotificationCompat.Builder(this@MyService)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
        }
        val id = System.currentTimeMillis().toInt()
        manager.notify(id,builder.build())


    }

}