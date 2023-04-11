package com.fekea.furniturestore.firebase.model

data class DBStore(
    var name: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
) {
    fun modelToHashMapOf(): HashMap<String, Any?> {
        return hashMapOf(
            "name" to name,
            "latitude" to latitude,
            "longitude" to longitude
        )
    }
}
