package com.fekea.furniturestore.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fekea.furniturestore.databinding.CardviewNotificationBinding
import com.fekea.furniturestore.notification.Notification

class NotificationAdapter (): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val adapterItems = arrayListOf<Notification>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CardviewNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NotificationViewHolder).bind(adapterItems[position])
    }

    override fun getItemCount(): Int {
        if (adapterItems == null){
            return 0
        }
        return adapterItems.size
    }

    fun addNotification(not: Notification) {
        adapterItems.add(not)
    }

    fun setupAdapterData(items: List<Notification>) {
        adapterItems.addAll(items)
        Log.e("TAG", "items added ${items.size}")
        notifyDataSetChanged()
    }
}