package com.fekea.furniturestore.notification

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.os.PowerManager
import android.util.Log

class NotificationService: Service() {

    companion object {
        const val TAG = "com.fekea.furniturestore:NotificationService"
        const val CMD = "not_cmd"
        const val CMD_ORDER_NOTIFICATION = 10
        const val CMD_ERROR_NOTIFICATION = 20
        const val CMD_ACCOUNT_NOTIFICATION = 30
        const val CMD_TEST_NOTIFICATION = 40
        const val CMD_SYSTEM_NOTIFICATION = 50
        const val CMD_ADVERTISING_NOTIFICATION = 60

        const val BROADCAST_ACTION = "com.fekea.furniturestore.message"

        const val KEY_TITLE = "MESSAGE_TITLE"
        const val KEY_MESSAGE = "MESSAGE_MSG"

        const val NOT_TITLE = "NOTIFICATION_TITLE"
        const val NOT_MESSAGE = "NOTIFICATION_MESSAGE"
    }

    lateinit var notificationMgr: NotificationManager
    lateinit var wakeLock: PowerManager.WakeLock
    lateinit var notificationPlayer: NotificationPlayer

    override fun onCreate() {
        Log.v(TAG, "onCreate()")
        super.onCreate()
        notificationMgr = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationPlayer = NotificationPlayer(this, notificationMgr)

        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"Fekea.Service:TG")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.v(TAG, "onStartCommand()")
        super.onStartCommand(intent, flags, startId)
        if (intent != null) {
            val data = intent.extras
            if (data != null) {
                handleData(data)
                if (!wakeLock.isHeld()) {
                    Log.v(TAG, "acquiring wake lock")
                    wakeLock?.acquire()
                }
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        Log.v(TAG, "onDestroy()")
        notificationMgr.cancelAll()
        Log.v(TAG, "releasing wake lock")
        wakeLock.release()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun handleData(data: Bundle) {
        val command = data.getInt(CMD)

        Log.d(TAG, "-(<- received command data to service: command=$command")
        if (command == CMD_ACCOUNT_NOTIFICATION) {
            val messageText = data[KEY_MESSAGE] as String
            val messageTitle = data[KEY_TITLE] as String
            notificationPlayer.displayExpandableNotification(
                0,
                messageTitle,
                messageText
            )
            sendBroadcastNewMessage(messageTitle, messageText)
        } else if (command == CMD_ORDER_NOTIFICATION) {
            val messageText = data[KEY_MESSAGE] as String
            val messageTitle = data[KEY_TITLE] as String
            notificationPlayer.displayExpandableNotification(
                0,
                messageTitle,
                messageText
            )
            sendBroadcastNewMessage(messageTitle, messageText)
        } else if (command == CMD_ERROR_NOTIFICATION) {
            val messageText = data[KEY_MESSAGE] as String
            val messageTitle = data[KEY_TITLE] as String
            notificationPlayer.displayExpandableNotification(
                0,
                messageTitle,
                messageText
            )
            sendBroadcastNewMessage(messageTitle, messageText)
        } else if (command == CMD_TEST_NOTIFICATION) {
            val messageText = data[KEY_MESSAGE] as String
            val messageTitle = data[KEY_TITLE] as String
            notificationPlayer.displayExpandableNotification(
                0,
                messageTitle,
                messageText
            )
            sendBroadcastNewMessage(messageTitle, messageText)
        } else if (command == CMD_SYSTEM_NOTIFICATION) {
            val messageText = data[KEY_MESSAGE] as String
            val messageTitle = data[KEY_TITLE] as String
            notificationPlayer.displayExpandableNotification(
                0,
                messageTitle,
                messageText
            )
            sendBroadcastNewMessage(messageTitle, messageText)
        } else if (command == CMD_ADVERTISING_NOTIFICATION) {
            val messageText = data[KEY_MESSAGE] as String
            val messageTitle = data[KEY_TITLE] as String
            notificationPlayer.displayExpandableNotification(
                0,
                messageTitle,
                messageText
            )
            sendBroadcastNewMessage(messageTitle, messageText)
        } else {
            Log.w(TAG, "Ignoring Unknown Command! id=$command")
        }
    }

    private fun sendBroadcastNewMessage(title: String, message: String) {
        Log.d(TAG, "->(+)<- sending broadcast: BROADCAST_NEW_MESSAGE")
        val intent = Intent()
        intent.action = BROADCAST_ACTION
        val data = Bundle()
        data.putString(NOT_TITLE, title)
        data.putString(NOT_MESSAGE, message)
        intent.putExtras(data)
        sendBroadcast(intent)
    }
}