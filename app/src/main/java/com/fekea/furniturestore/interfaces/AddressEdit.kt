package com.fekea.furniturestore.interfaces

import com.fekea.furniturestore.firebase.model.DBUserAddress

interface AddressEdit {
    fun onAddressClicked(address: DBUserAddress)
}