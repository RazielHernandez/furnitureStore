package com.fekea.furniturestore.firebase.model

import com.google.firebase.firestore.Exclude

data class DBUser(

    var name: String = "null",
    var lastName: String = "",
    var login: String = "",
    var password: String = "",
    var email: String = "",
    var enable: Boolean = false,
    var telephone: String = "",
    var type: String = "",
    var addresses: ArrayList<DBUserAddress> = ArrayList(),
    var creditCards: ArrayList<DBUserCreditCard> = ArrayList(),
    var favorites: ArrayList<String> = ArrayList(),
    var basket: ArrayList<DBFurniture> = ArrayList(),
){

    fun modelToHashMapOf(): HashMap<String, Any?> {
        return hashMapOf(
            "name" to name,
            "lastName" to lastName,
            "login" to login,
            "password" to password,
            "email" to email,
            "enable" to enable,
            "telephone" to telephone,
            "type" to type,
            "addresses" to addresses,
            "creditCards" to creditCards,
            "favorites" to favorites,
            "basket" to basket,
        )
    }

    @Exclude
    fun isLogged(): Boolean {
        if (type.isEmpty()) {
            return false
        }
        return true
    }
}
