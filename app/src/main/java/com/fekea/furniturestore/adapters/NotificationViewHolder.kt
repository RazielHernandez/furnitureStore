package com.fekea.furniturestore.adapters

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.fekea.furniturestore.databinding.CardviewNotificationBinding
import com.fekea.furniturestore.notification.Notification

class NotificationViewHolder(private val binding: CardviewNotificationBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Notification) {
        Log.e("HOLDER", "Init card ${item.date}")
        binding.notificationTitle.text = item.title
        binding.notificationMessage.text = item.message
        binding.notificationDate.text = item.date
    }
}