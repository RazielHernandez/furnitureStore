package com.fekea.furniturestore.firebase.model

data class DBFurnitureDimensions(
    var depth: Float = 0.0F,
    var width: Float = 0.0F,
    var height: Float = 0.0F,
    var unit: String = ""
){
    fun modelToHashMapOf(): HashMap<String, Any?> {
        return hashMapOf(
            "depth" to depth,
            "width" to width,
            "height" to height,
            "unit" to unit
        )
    }

}
