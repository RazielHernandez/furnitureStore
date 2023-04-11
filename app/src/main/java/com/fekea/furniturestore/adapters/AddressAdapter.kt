package com.fekea.furniturestore.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fekea.furniturestore.databinding.CardviewAddressBinding
import com.fekea.furniturestore.firebase.model.DBUserAddress
import com.fekea.furniturestore.interfaces.AddressEdit

class AddressAdapter (private val listener: AddressEdit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val adapterItems = arrayListOf<DBUserAddress>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CardviewAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AddressViewHolder).bind(adapterItems[position])
    }

    override fun getItemCount(): Int {
        if (adapterItems == null){
            return 0
        }
        return adapterItems.size
    }

    fun addAddress(address: DBUserAddress) {
        adapterItems.add(address)
        notifyDataSetChanged()
    }

    fun removeAddress(address: DBUserAddress) {
        adapterItems.remove(address)
        notifyDataSetChanged()
    }

    fun clearAdapterData() {
        adapterItems.clear()
    }

    fun setupAdapterData(items: List<DBUserAddress>) {
        adapterItems.addAll(items)
        Log.e("TAG", "items added ${items.size}")
        notifyDataSetChanged()
    }
}