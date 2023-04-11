package com.fekea.furniturestore.firebase.model

data class DBUserCreditCard(
    var name: String = "",
    var owner: String = "",
    var number: String = "",
    var expireDate: String = "",
    var cvv: String = ""
){
    fun modelToHashMapOf(): HashMap<String, Any?> {
        return hashMapOf(
            "name" to name,
            "owner" to owner,
            "number" to number,
            "expireDate" to expireDate,
            "cvv" to cvv,
        )
    }
}
