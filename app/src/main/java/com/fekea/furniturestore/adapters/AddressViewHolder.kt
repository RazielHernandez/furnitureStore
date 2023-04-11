package com.fekea.furniturestore.adapters

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.fekea.furniturestore.databinding.CardviewAddressBinding
import com.fekea.furniturestore.firebase.model.DBUserAddress
import com.fekea.furniturestore.interfaces.AddressEdit

class AddressViewHolder(private val binding: CardviewAddressBinding, private val listener: AddressEdit): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DBUserAddress) {
        Log.e("HOLDER", "Init card ${item.name}")
        binding.addressName.text = item.name
        binding.addressAddress.text = item.address
        binding.addressPostalCode.text = item.postalCode
        binding.addressTelephone.text = item.telephone
        binding.addressCard.setOnClickListener {
            listener.onAddressClicked(item)
        }
    }
}