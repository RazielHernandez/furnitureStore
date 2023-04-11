package com.fekea.furniturestore.adapters

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.fekea.furniturestore.databinding.CardviewFurnitureBasketBinding
import com.fekea.furniturestore.firebase.model.DBFurniture
import com.fekea.furniturestore.interfaces.FavoriteRemove
import com.fekea.furniturestore.interfaces.ItemClickEvent
import com.fekea.furniturestore.interfaces.ShoppingCarModifier
import com.squareup.picasso.Picasso

class ShoppingCarViewHolder(
    private val binding: CardviewFurnitureBasketBinding,
    private val shoppingCarModifier: ShoppingCarModifier
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DBFurniture) {
        Log.e("HOLDER", "Init card ${item.id}")
        binding.cardTitle.text = item.model
        binding.cardPrice.text = "${item.totalPriceFormatted()}"
        binding.cardQuantity.text = item.quantity.toString()

        if (item.images.isNotEmpty()){
            Picasso.with(binding.root.context).load(item.images[0]).into(binding.cardImage)
        }

        binding.cardButtonPlus.setOnClickListener {
            shoppingCarModifier.onItemIncrease(item)
        }

        binding.cardButtonMinus.setOnClickListener {
            shoppingCarModifier.onItemDecrease(item)
        }
    }
}