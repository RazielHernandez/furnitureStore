package com.fekea.furniturestore.firebase.model

data class DBUserAddress(
    var name: String = "",
    var address: String = "",
    var postalCode: String = "",
    var telephone: String = ""
){
    fun modelToHashMapOf(): HashMap<String, Any?> {
        return hashMapOf(
            "name" to name,
            "address" to address,
            "postalCode" to postalCode,
            "telephone" to telephone,
        )
    }
}
