package com.fekea.furniturestore.adapters

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.fekea.furniturestore.databinding.CardviewFurnitureSmallBinding
import com.fekea.furniturestore.firebase.model.DBFurniture
import com.fekea.furniturestore.interfaces.ItemClickEvent
import com.squareup.picasso.Picasso

class SmallFurnitureViewHolder(private val binding: CardviewFurnitureSmallBinding, private val listener: ItemClickEvent): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DBFurniture) {
        Log.e("HOLDER", "Init card ${item.id}")
        binding.cardTitle.text = item.model
        binding.cardRating.text = item.rating.toString()
        binding.cardPrice.text = "$ ${item.price}"
        if (item.images.isNotEmpty()){
            Picasso.with(binding.root.context).load(item.images[0]).into(binding.cardImage)
        }

        binding.cardImage.setOnClickListener {
            listener.onItemClicked(item.id)
        }
    }
}