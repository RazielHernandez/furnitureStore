package com.fekea.furniturestore.firebase.model

data class DBOrder(
    var orderDate: String = "",
    var user: String = "",
    var total: Float = 0F,
    var status: String = "Pending",
    var shipmentDate: String = "",
    var deliveryDate: String = "",
    var items: ArrayList<DBFurniture> = arrayListOf(),
) {
    fun modelToHashMapOf(): HashMap<String, Any?> {
        return hashMapOf(
            "orderDate" to orderDate,
            "user" to user,
            "total" to total,
            "status" to status,
            "shipmentDate" to shipmentDate,
            "deliveryDate" to deliveryDate,
            "items" to items,
        )
    }
}
