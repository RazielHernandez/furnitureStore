package com.fekea.furniturestore.activities

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.fekea.furniturestore.R
import com.fekea.furniturestore.firebase.model.DBFurniture
import com.fekea.furniturestore.firebase.service.FurnitureModel
import com.fekea.furniturestore.firebase.service.UserModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso


class ItemDetailActivity: AppCompatActivity() {

    var itemSelected: DBFurniture = DBFurniture()
    private lateinit var furnitureModel: FurnitureModel
    private lateinit var userModel: UserModel

    lateinit var itemImage: ImageView
    lateinit var itemName: TextView
    lateinit var itemRating: TextView
    lateinit var itemRatingImages: MutableList<ImageView>
    lateinit var itemDescription: TextView
    lateinit var itemDimensionD: TextView
    lateinit var itemDimensionW: TextView
    lateinit var itemDimensionH: TextView
    lateinit var itemColors: TextView
    lateinit var buttonMinus: Button
    lateinit var buttonPlus: Button
    lateinit var button3DModel: Button
    lateinit var buttonMap: Button
    lateinit var buttonFavorite: Button
    lateinit var buttomBuy: FloatingActionButton
    lateinit var itemQuantity: TextView
    lateinit var itemDetails: TextView
    lateinit var itemPrice: TextView
    lateinit var itemDiscount: TextView
    lateinit var itemSale: TextView
    private var quantity: Int = 1

    companion object {
        const val TAG = "com.fekea.furniturestore.ItemDetailActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        itemRatingImages = mutableListOf()

        userModel = ViewModelProvider(this)[UserModel::class.java]
        setupLiveDataListener()

        furnitureModel = ViewModelProvider(this)[FurnitureModel::class.java]
        val id = intent.getStringExtra("id")

        if (id != null) {
            Log.e(TAG,"Id $id was received")
            furnitureModel.getFurnitureByLogin(id)
            furnitureModel.furnitureLiveData.observe(this) {
                itemSelected = it
                showData()
            }


        }
    }

    private fun showData() {

        Log.e(TAG, "Loading the data")

        itemImage = findViewById(R.id.detail_images)
        itemName = findViewById(R.id.detail_name)
        itemRating = findViewById(R.id.detail_rating_score)
        itemRatingImages.add(findViewById(R.id.detail_rating_first_star))
        itemRatingImages.add(findViewById(R.id.detail_rating_second_star))
        itemRatingImages.add(findViewById(R.id.detail_rating_third_star))
        itemRatingImages.add(findViewById(R.id.detail_rating_fourth_star))
        itemRatingImages.add(findViewById(R.id.detail_rating_fifth_star))
        itemDescription = findViewById(R.id.detail_short_description)
        itemDimensionD = findViewById(R.id.detail_dimension_d)
        itemDimensionW = findViewById(R.id.detail_dimension_w)
        itemDimensionH = findViewById(R.id.detail_dimension_h)
        itemColors = findViewById(R.id.detail_colors)
        itemQuantity = findViewById(R.id.detail_quantity)
        itemDetails = findViewById(R.id.detail_long_description)
        itemPrice = findViewById(R.id.detail_price)
        itemDiscount = findViewById(R.id.detail_discount)
        itemSale = findViewById(R.id.detail_sale)
        buttonMap = findViewById(R.id.detail_button_map)
        button3DModel = findViewById(R.id.detail_button_3d)
        buttonPlus = findViewById(R.id.detail_button_plus)
        buttonMinus = findViewById(R.id.detail_button_minus)
        buttomBuy = findViewById(R.id.detail_button_buy)
        buttonFavorite = findViewById(R.id.detail_button_favorite)

        itemName.text = itemSelected.category
        itemRating.text = itemSelected.rating.toString()
        itemDescription.text = itemSelected.shortDescription
        itemDimensionD.text = "D: ${itemSelected.dimensions.depth} ${itemSelected.dimensions.unit} "
        itemDimensionW.text = "W: ${itemSelected.dimensions.width} ${itemSelected.dimensions.unit} "
        itemDimensionH.text = "H: ${itemSelected.dimensions.height} ${itemSelected.dimensions.unit} "
        itemColors.text = itemSelected.getColorsString()
        itemQuantity.text = quantity.toString()
        itemDetails.text = itemSelected.longDescription

        itemPrice.text = itemSelected.priceFormatted()
        if (itemSelected.discount > 0) {
            itemPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemDiscount.text = itemSelected.discountFormatted()
            itemDiscount.visibility = View.VISIBLE
            itemSale.text = itemSelected.saleFormatted()
            itemSale.visibility = View.VISIBLE
        }else {
            itemPrice.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            itemDiscount.visibility = View.GONE
            itemSale.visibility = View.GONE
        }

        setFavoriteIcon()

        if (itemSelected.images.size > 0) {
            Picasso.with(this).load(itemSelected.images[0]).into(itemImage)
        }

        buttonMap.setOnClickListener {
            Log.e(TAG, "to Map Activity")
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("stores", itemSelected.stores)
            intent.putExtra("item", itemSelected.category)
            startActivity(intent)
        }

        if (itemSelected.model3d.isEmpty()) {
            button3DModel.isEnabled = false
        }else {
            button3DModel.isEnabled = true
            button3DModel.setOnClickListener {
                Log.e(TAG, "to 3D model view")
                val intent = Intent(this, ARActivity::class.java)
                intent.putExtra("modelName", itemSelected.model3d)
                startActivity(intent)
            }
        }


        buttonMinus.setOnClickListener {
            if (quantity > 1) {
                quantity -= 1
                itemQuantity.text = quantity.toString()
            }
        }

        buttonPlus.setOnClickListener {
            if (quantity < 20) {
                quantity += 1
                itemQuantity.text = quantity.toString()
            }
        }

        buttonFavorite.setOnClickListener {
            Log.e(TAG, "favorite pressed")
            if (MainActivity.user?.favorites?.contains(itemSelected.id) == true){
                Log.e(TAG, "delete favorite")
                MainActivity.user?.favorites?.remove(itemSelected.id)
            }else{
                Log.e(TAG, "adding favorite")
                MainActivity.user?.favorites?.add(itemSelected.id)
            }
            MainActivity.user?.let { userModel.updateUser(it) }
        }

        buttomBuy.setOnClickListener {
            Log.e(TAG, "buy item")
            itemSelected.quantity = quantity

            if (MainActivity.user?.basket?.contains(itemSelected) == true) {
                val index = MainActivity.user!!.basket.indexOf(itemSelected)
                MainActivity.user!!.basket[index].quantity += quantity
            }else {
                MainActivity.user?.basket?.add(itemSelected)
            }

            MainActivity.user?.let { it1 -> userModel.updateUser(it1) }
            showBuyDialog()
        }
    }

    private fun setFavoriteIcon() {
        if (MainActivity.user?.favorites?.contains(itemSelected.id) == true){
            val img: Drawable? = buttonFavorite.context.getDrawable(R.drawable.ic_star_complete_24)
            buttonFavorite.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null)
        }else {
            val img: Drawable? = buttonFavorite.context.getDrawable(R.drawable.ic_star_gray_24)
            buttonFavorite.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null)
        }
    }

    private fun setupLiveDataListener() {
        userModel.completeLiveData.observe(this) {
            Log.e(AddressActivity.TAG, "Was completed successfully: $it")
            setFavoriteIcon()
        }
    }

    private fun showBuyDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = this?.layoutInflater
        builder.setTitle("Item added")
        val dialogLayout = inflater?.inflate(R.layout.dialog_item_added, null)
        val textMessage  = dialogLayout?.findViewById<TextView>(R.id.added_message)
        textMessage?.setText("Your item was added to your shopping car")

        builder.setView(dialogLayout)
        builder.setPositiveButton("Ok") { dialogInterface, i ->
        }
        builder.show()
    }

}