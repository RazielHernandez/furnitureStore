package com.fekea.furniturestore.adapters

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.fekea.furniturestore.databinding.CardviewFavoriteBinding
import com.fekea.furniturestore.databinding.CardviewFurnitureSmallBinding
import com.fekea.furniturestore.firebase.model.DBFurniture
import com.fekea.furniturestore.interfaces.FavoriteRemove
import com.fekea.furniturestore.interfaces.ItemClickEvent
import com.squareup.picasso.Picasso

class FavoriteViewHolder(
    private val binding: CardviewFavoriteBinding,
    private val listenerItem: ItemClickEvent,
    private val listenerFavorite: FavoriteRemove): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DBFurniture) {
        Log.e("HOLDER", "Init card ${item.id}")
        binding.cardTitle.text = item.model
        binding.cardRating.text = item.rating.toString()
        binding.cardPrice.text = "$ ${item.price}"
        if (item.images.isNotEmpty()){
            Picasso.with(binding.root.context).load(item.images[0]).into(binding.cardImage)
        }

        binding.cardImage.setOnClickListener {
            listenerItem.onItemClicked(item.id)
        }

        binding.cardFavoriteButton.setOnClickListener {
            listenerFavorite.onFavoriteRemove(item.id)
        }
    }

}