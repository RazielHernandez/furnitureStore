package com.fekea.furniturestore.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fekea.furniturestore.databinding.CardviewAddressBinding
import com.fekea.furniturestore.databinding.CardviewCreditcardBinding
import com.fekea.furniturestore.firebase.model.DBUserAddress
import com.fekea.furniturestore.firebase.model.DBUserCreditCard
import com.fekea.furniturestore.interfaces.AddressEdit
import com.fekea.furniturestore.interfaces.CreditcardEdit

class CreditCardAdapter (private val listener: CreditcardEdit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val adapterItems = arrayListOf<DBUserCreditCard>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CardviewCreditcardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CreditCardViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CreditCardViewHolder).bind(adapterItems[position])
    }

    override fun getItemCount(): Int {
        if (adapterItems == null){
            return 0
        }
        return adapterItems.size
    }

    fun addCreditCard(cc: DBUserCreditCard) {
        adapterItems.add(cc)
        notifyDataSetChanged()
    }

    fun removeCreditCard(card: DBUserCreditCard) {
        adapterItems.remove(card)
        notifyDataSetChanged()
    }

    fun clearAdapterData() {
        adapterItems.clear()
    }

    fun setupAdapterData(items: List<DBUserCreditCard>) {
        adapterItems.addAll(items)
        Log.e("TAG", "items added ${items.size}")
        notifyDataSetChanged()
    }

}