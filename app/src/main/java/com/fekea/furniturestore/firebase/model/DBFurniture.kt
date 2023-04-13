package com.fekea.furniturestore.firebase.model

import android.util.Log
import com.google.firebase.firestore.Exclude
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

data class DBFurniture(
    var id: String = "",
    var brand: String = "",
    var model: String = "",
    var size: String = "",
    var style: String = "",
    var colors: ArrayList<DBFurnitureColor> = ArrayList(),
    var room: String = "",
    var dimensions: DBFurnitureDimensions = DBFurnitureDimensions(),
    var shortDescription: String = "",
    var longDescription: String = "",
    var images: ArrayList<String> = ArrayList(),
    var price: Float = 0.0F,
    var discount: Float = 0F,
    var rating: Float = 0.0F,
    var category: String = "General",
    var material: String = "",
    var stores: ArrayList<String> = ArrayList(),
    var quantity: Int = 0,
    var model3d: String = ""
){
    fun modelToHashMapOf(): HashMap<String, Any?> {
        return hashMapOf(
            "id" to id,
            "brand" to brand,
            "model" to model,
            "size" to size,
            "style" to style,
            "colors" to colors,
            "room" to room,
            "dimensions" to dimensions,
            "shortDescription" to shortDescription,
            "longDescription" to longDescription,
            "images" to images,
            "price" to price,
            "discount" to discount,
            "rating" to rating,
            "category" to category,
            "material" to material,
            "stores" to stores,
            "quantity" to quantity,
            "model3d" to model3d
        )
    }

    @Exclude
    fun sortByWord(query: String): Float {
        var result = 0.0F
        if (category.lowercase().contains(query.lowercase())) {
            result += 5F
        } else if (room.lowercase().contains(query.lowercase())) {
            result += 4F
        } else if (model.lowercase().contains(query.lowercase())) {
            result += 3.5F
        } else if (shortDescription.lowercase().contains(query.lowercase())) {
            result += 3F
        } else if (longDescription.lowercase().contains(query.lowercase())) {
            result += 2F
        }
        Log.e("TAG", "Item $model has search value: $result")
        return result
    }

    @Exclude
    fun searchWord(query: String): Boolean {
        var result = false
        if (category.lowercase().contains(query.lowercase())) {
            result = true
        } else if (room.lowercase().contains(query.lowercase())) {
            result = true
        } else if (model.lowercase().contains(query.lowercase())) {
            result = true
        } else if (shortDescription.lowercase().contains(query.lowercase())) {
            result = true
        } /*else if (longDescription.lowercase().contains(query.lowercase())) {
            result = true
        }*/
        Log.e("TAG", "Item $model has search value: $result")
        return result
    }

    @Exclude
    fun getDimensions(): String {
        return "D: ${dimensions.depth} x W: ${dimensions.width} x H: ${dimensions.height} ${dimensions.unit}"
    }

    @Exclude
    fun getColorsString(): String {
        var result = ""
        for (color in colors) {
            result += "${color.name} "
        }
        return result
    }

    @Exclude
    fun priceFormatted(): String {
        val formatter: NumberFormat = DecimalFormat("$ #,###.##")
        return formatter.format(price)
    }

    @Exclude
    fun discountFormatted(): String {
        val formatter = NumberFormat.getPercentInstance(Locale.US)
        return formatter.format(discount/100)
    }

    @Exclude
    fun saleFormatted(): String {
        val formatter: NumberFormat = DecimalFormat("$ #,###.##")
        return formatter.format(price - (price * (discount / 100F)))
    }

    @Exclude
    fun totalPriceFormatted(): String {
        val formatter: NumberFormat = DecimalFormat("$ #,###.##")
        return formatter.format((price - (price * (discount / 100F))) * quantity)
    }

    @Exclude
    fun totalPrice(): Float {
        return ((price - (price * (discount / 100F))) * quantity)
    }
}
