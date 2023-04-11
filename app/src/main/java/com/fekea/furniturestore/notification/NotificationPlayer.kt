package com.fekea.furniturestore.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.fekea.furniturestore.activities.MainActivity
import com.fekea.furniturestore.R

class NotificationPlayer(context: Context, notificationManager: NotificationManager) {

    companion object {
        const val TAG = "com.fekea.furniturestore.NotificationPlayer"
    }

    lateinit var notificationMgr: NotificationManager
    lateinit var appContext: Context

    init {
        appContext = context
        notificationMgr = notificationManager
        createNotificationChannel()
    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel.
            val name = R.string.channel_name.toString()
            val descriptionText = R.string.channel_description.toString()
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(R.string.channel_id.toString(), name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            val notificationManager = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    fun displaySimpleNotification(id: Int?, title: String?, contentText: String?) {
        //if (messageNotifierConfig.isPlaySound()) {
            Log.e(TAG, "displaySimpleNotification")
            val intent = Intent(appContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP
            val contentIntent = PendingIntent.getActivity(
                appContext, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT
            )

            // notification message
            try {
                Log.e(TAG, "notification")
                /*val remoteViews = RemoteViews(context.getPackageName(), R.layout.notification_view)
                remoteViews.setImageViewResource(R.id.image_left, R.drawable.ic_baseline_email_24)
                remoteViews.setImageViewResource(R.id.image_right, R.drawable.ic_baseline_email_24)
                remoteViews.setTextViewText(R.id.title, title)
                remoteViews.setTextViewText(R.id.text, contentText)*/
                val builder: NotificationCompat.Builder =
                    NotificationCompat.Builder(appContext, R.string.channel_id.toString())
                        .setSmallIcon(R.drawable.ic_shopping_basket)
                        .setContentTitle(title)
                        .setContentText(contentText)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(contentIntent)
                        .setAutoCancel(true)

                notificationMgr.notify(id!!, builder.build())
            } catch (e: IllegalArgumentException) {
                Log.e(TAG, e.message!!)
            }
        //}
    }

    fun displayExpandableNotification(id: Int?, title: String?, contentText: String?) {
        //if (messageNotifierConfig.isPlaySound()) {
        Log.e(TAG, "displayExpandableNotification")
        val intent = Intent(appContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_SINGLE_TOP
        val contentIntent = PendingIntent.getActivity(
            appContext, 0,
            intent, PendingIntent.FLAG_IMMUTABLE
        )

        // notification message
        try {
            /*val remoteViews = RemoteViews(context.getPackageName(), R.layout.notification_view)
            remoteViews.setImageViewResource(R.id.image_left, R.drawable.ic_message)
            remoteViews.setImageViewResource(R.id.image_right, R.drawable.ic_baseline_email_24)
            remoteViews.setTextViewText(R.id.title, "GeoChat App")
            remoteViews.setTextViewText(R.id.subtitle, title)
            remoteViews.setTextViewText(R.id.text, contentText)*/
            val builder: NotificationCompat.Builder =
                NotificationCompat.Builder(appContext, R.string.channel_id.toString())
                    .setSmallIcon(R.drawable.ic_shopping_basket)
                    .setContentTitle(title)
                    .setContentText(contentText)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Set the intent that will fire when the user taps the notification
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)

            notificationMgr.notify(id!!, builder.build())
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, e.message!!)
        }
        //}
    }
}