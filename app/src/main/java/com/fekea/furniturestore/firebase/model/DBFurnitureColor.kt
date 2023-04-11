package com.fekea.furniturestore.firebase.model

import android.graphics.Color

data class DBFurnitureColor(
    var name: String = "",
    var colorRed: Int = 0,
    var colorGreen: Int = 0,
    var colorBlue: Int = 0,
    var colorOpacity: Int = 0
){
    fun modelToHashMapOf(): HashMap<String, Any?> {
        return hashMapOf(
            "name" to name,
            "colorRed" to colorRed,
            "colorGreen" to colorGreen,
            "colorBlue" to colorBlue,
            "colorOpacity" to colorOpacity,
        )
    }
}
