package com.fekea.furniturestore.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fekea.furniturestore.databinding.CardviewFavoriteBinding
import com.fekea.furniturestore.databinding.CardviewFurnitureSmallBinding
import com.fekea.furniturestore.firebase.model.DBFurniture
import com.fekea.furniturestore.interfaces.FavoriteRemove
import com.fekea.furniturestore.interfaces.ItemClickEvent

class FavoriteAdapter(
    private val listenerItem: ItemClickEvent,
    private val listenerFavorite: FavoriteRemove): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val adapterItems = arrayListOf<DBFurniture>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CardviewFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding, listenerItem, listenerFavorite)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FavoriteViewHolder).bind(adapterItems[position])
    }

    override fun getItemCount(): Int {
        return adapterItems.size
    }

    fun clearAdapterData() {
        adapterItems.clear()
    }

    fun setupAdapterData(items: List<DBFurniture>) {
        adapterItems.addAll(items)
        Log.e("TAG", "items added ${items.size}")
        notifyDataSetChanged()
    }

}