package com.fekea.furniturestore.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fekea.furniturestore.databinding.CardviewFurnitureSmallBinding
import com.fekea.furniturestore.firebase.model.DBFurniture
import com.fekea.furniturestore.interfaces.ItemClickEvent

class SmallFurnitureAdapter(private val listener: ItemClickEvent): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val adapterItems = arrayListOf<DBFurniture>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CardviewFurnitureSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SmallFurnitureViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SmallFurnitureViewHolder).bind(adapterItems[position])
    }

    override fun getItemCount(): Int {
        return adapterItems.size
    }

    fun setupAdapterData(items: List<DBFurniture>) {
        adapterItems.addAll(items)
        Log.e("TAG", "items added ${items.size}")
        notifyDataSetChanged()
    }
}