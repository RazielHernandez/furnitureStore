package com.fekea.furniturestore.interfaces

import com.fekea.furniturestore.firebase.model.DBUserCreditCard

interface FavoriteRemove {
    fun onFavoriteRemove(furniture: String)
}