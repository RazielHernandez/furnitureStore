package com.fekea.furniturestore.interfaces

import com.fekea.furniturestore.firebase.model.DBFurniture

interface ShoppingCarModifier {
    fun onItemIncrease(furniture: DBFurniture)
    fun onItemDecrease(furniture: DBFurniture)
}