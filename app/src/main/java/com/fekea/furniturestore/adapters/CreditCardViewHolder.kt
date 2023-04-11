package com.fekea.furniturestore.adapters

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.fekea.furniturestore.databinding.CardviewCreditcardBinding
import com.fekea.furniturestore.firebase.model.DBUserCreditCard
import com.fekea.furniturestore.interfaces.CreditcardEdit

class CreditCardViewHolder(private val binding: CardviewCreditcardBinding, private val listener: CreditcardEdit): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: DBUserCreditCard) {
        Log.e("HOLDER", "Init card ${item.name}")
        binding.cardName.text = item.name
        binding.cardNumber.text = item.number
        binding.cardExpireDate.text = item.expireDate
        binding.cardCvv.text = "***"
        binding.cardCard.setOnClickListener {
            listener.onCreditCardClicked(item)
        }
    }
}