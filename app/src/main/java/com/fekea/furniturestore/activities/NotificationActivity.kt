package com.fekea.furniturestore.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fekea.furniturestore.R
import com.fekea.furniturestore.adapters.NotificationAdapter
import com.fekea.furniturestore.notification.Notification
import com.fekea.furniturestore.notification.NotificationService
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotificationActivity: AppCompatActivity() {

    private lateinit var notificationAdapter: NotificationAdapter

    companion object {
        const val TAG = "com.fekea.furniturestore.NotificationActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        val recycleView = findViewById<RecyclerView>(R.id.recycle_view_notification)
        recycleView.layoutManager = LinearLayoutManager(this)
        notificationAdapter = NotificationAdapter()
        recycleView.adapter = notificationAdapter

        val button = findViewById<FloatingActionButton>(R.id.notification_button)
        button.setOnClickListener {
            sendTestMessage()
        }

        registerServiceStateChangeReceiver()

    }

    private fun sendTestMessage() {
        val data = Bundle()
        data.putInt(NotificationService.CMD, NotificationService.CMD_TEST_NOTIFICATION)
        data.putString(NotificationService.KEY_TITLE, "Test")
        data.putString(NotificationService.KEY_MESSAGE, "This is a test notification. Please ignore it")
        val intent = Intent(this, NotificationService::class.java)
        intent.putExtras(data)
        startService(intent)
    }

    fun displayNotification(message: Notification?) {
        if (message != null) {
            notificationAdapter.addNotification(message)
            notificationAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mServiceStateChangeReceiver)
    }


    /**
     * Broadcaster for Notifications
     */
    private val mServiceStateChangeReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        private val TAG = "BroadcastReceiver"

        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            val data = intent.extras
            Log.d(TAG, "received broadcast message from service: $action")
            if (NotificationService.BROADCAST_ACTION.equals(action)) {
                val title = data?.getString(NotificationService.NOT_TITLE)
                val message = data?.getString(NotificationService.NOT_MESSAGE)
                val not = Notification(
                    date = "04/08/2023",
                    title = title.toString(),
                    message = message.toString(),
                    cmd = action.toString()
                )
                displayNotification(not)
                Log.e(TAG, "Broadcast receiver with $title: $message")
            } else {
                Log.v(TAG, "do nothing for action: $action")
            }
        }
    }


    private fun registerServiceStateChangeReceiver() {
        Log.d(DatabaseActivity.TAG, "registering service state change receiver...")
        val intentFilter = IntentFilter()
        intentFilter.addAction(NotificationService.BROADCAST_ACTION)
        registerReceiver(mServiceStateChangeReceiver, intentFilter)
    }
}