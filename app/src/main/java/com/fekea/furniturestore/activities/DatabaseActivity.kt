package com.fekea.furniturestore.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.fekea.furniturestore.R
import com.fekea.furniturestore.firebase.model.*
import com.fekea.furniturestore.firebase.service.FurnitureDatabase
import com.fekea.furniturestore.firebase.service.FurnitureModel
import com.fekea.furniturestore.firebase.service.UserModel
import com.fekea.furniturestore.notification.NotificationService

class DatabaseActivity: AppCompatActivity() {

    private lateinit var userViewModel: UserModel
    private lateinit var furnitureModel: FurnitureModel
    private lateinit var actualUser: DBUser

    companion object {
        const val TAG = "com.fekea.furniturestore.activities.MainActivity"

        var db: FurnitureDatabase? = null
    }

    /*override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mServiceStateChangeReceiver)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_database)

        db = FurnitureDatabase(applicationContext)
        userViewModel = ViewModelProvider(this)[UserModel::class.java]
        furnitureModel = ViewModelProvider(this)[FurnitureModel::class.java]
        setupLiveDataListener()

        //registerServiceStateChangeReceiver()

        val newUser = DBUser(
            name = "User",
            lastName = "Test 2",
            login = "user2@mail.com",
            password = "usertest2",
            email = "user2@mail.com",
            telephone = "444 123 0000",
            enable = true,
            type = "App",
        )

        val firstAddress = DBUserAddress(
            name = "Home",
            address = "2626 Eglinton Av East",
            postalCode = "M1K 2S3",
            telephone = "444 123 0000"
        )

        val secondAddress = DBUserAddress(
            name = "Parents's Home",
            address = "2624 Eglinton Av East",
            postalCode = "M1K 2S3",
            telephone = "444 123 0000"
        )

        val firstCreditCard = DBUserCreditCard(
            name = "Prefered",
            owner = "Raziel Hernandez",
            number = "0000 0000 0000 0000",
            expireDate = "0124",
            cvv = "111"
        )

        val newFurniture = DBFurniture(
            id = "LR00000009",
            brand = "HAHRIR",
            model = "20x32",
            size = "20 x 32",
            style = "Rustic",
            room = "Living room",
            shortDescription = "Coffee Table End Table Nightstand Industrial 2-Tier Tea Table with Storage Shelf Accent Furniture for Living Room, Rustic Brown",
            longDescription = "【Durable, black metal frame, double-layer board can provide more storage space.\n" +
                    "The coffee table is of appropriate size and can be flexibly placed in different places.\n" +
                    "The coffee table size is 31.5\"L*20.08\"W*16.53\"H.\n" +
                    "All parts are packed in a packing box and marked with dimensions. Please install it easily according to the instructions.\n" +
                    "We have a complete after-sales service system, you can contact us at any time if you have any questions, and we will solve it for you.",
            price = 99.99F,
            discount = 0F,
            rating = 4.1F,
            category = "Coffee Table",
            material = "Metal"
        )

        val firstFurnitureColor = DBFurnitureColor(
            name = "Brown",
            colorRed = 102,
            colorGreen = 34,
            colorBlue = 0,
            colorOpacity = 1
        )

        val secondFurnitureColor = DBFurnitureColor(
            name = "Oak",
            colorRed = 255,
            colorGreen = 255,
            colorBlue = 240,
            colorOpacity = 1
        )

        val thirdFurnitureColor = DBFurnitureColor(
            name = "White",
            colorRed = 255,
            colorGreen = 255,
            colorBlue = 255,
            colorOpacity = 1
        )

        val newFurnitureDimensions = DBFurnitureDimensions(
            depth = 0F,
            width = 81.3F,
            height = 177.8F,
            "Centimetres"
        )

        newFurniture.images.add("https://m.media-amazon.com/images/W/IMAGERENDERING_521856-T2/images/I/91L67rFjDTS._AC_SX679_.jpg")
        newFurniture.images.add("https://m.media-amazon.com/images/W/IMAGERENDERING_521856-T2/images/I/91hALyTyM3S._AC_SX679_.jpg")
        newFurniture.images.add("https://m.media-amazon.com/images/W/IMAGERENDERING_521856-T2/images/I/9125TQTqkeS._AC_SX679_.jpg")
        newFurniture.images.add("https://m.media-amazon.com/images/W/IMAGERENDERING_521856-T2/images/I/71figR0NWJS._AC_SX679_.jpg")
        //newFurniture.images.add("https://m.media-amazon.com/images/W/IMAGERENDERING_521856-T2/images/I/91V0ZPk5cXL._AC_SX679_.jpg")
        //newFurniture.images.add("https://m.media-amazon.com/images/W/IMAGERENDERING_521856-T2/images/I/91MyxaToxqL._AC_SX679_.jpg")

        /*
        newUser.creditCards.add(firstCreditCard)
        newUser.addresses.add(firstAddress)
        newUser.addresses.add(secondAddress)
        */

        //userViewModel.insertUser(newUser)

        //insertUser(newUser)
        //insertAddress(newUser.login, newAddress)

        //userViewModel.getUserByLogin("user1@mail.com")
        //userViewModel.insertUser(newUser)

        //furnitureModel.insertFurniture(newFurniture)
        //newFurniture.colors.add(newFurnitureColor)
        //furnitureModel.updateFurniture(newFurniture)
        //furnitureModel.deleteFurniture("3")
        //furnitureModel.getFurnitureByLogin("2")

        newFurniture.colors.add(firstFurnitureColor)
        newFurniture.colors.add(secondFurnitureColor)
        //newFurniture.colors.add(thirdFurnitureColor)
        //newFurniture.dimensions = newFurnitureDimensions

        furnitureModel.insertFurniture(newFurniture)
    }

    private fun setupLiveDataListener() {
        userViewModel.userLiveData.observe(this) {
            Log.e(TAG, "=========================================")
            Log.e(TAG, "User name: ${it.name}")
            Log.e(TAG, "User lastname: ${it.lastName}")
            Log.e(TAG, "User login: ${it.login}")
            Log.e(TAG, "User password: ${it.password}")
            Log.e(TAG, "User mail: ${it.email}")
            Log.e(TAG, "User enabled: ${it.enable}")
            Log.e(TAG, "User telephone: ${it.telephone}")
            Log.e(TAG, "User type: ${it.type}")
            Log.e(TAG, "Addresses: ${it.addresses.size}")
            Log.e(TAG, "Credit Cards: ${it.creditCards.size}")
            Log.e(TAG, "=========================================")

            /*actualUser = it
            if (actualUser.type != "null"){
                val firstAddress = DBUserAddress(
                    name = "Home",
                    address = "2626 Eglinton Av East",
                    postalCode = "M1K 2S3",
                    telephone = "444 123 0000"
                )
                actualUser.addresses.add(firstAddress)
                userViewModel.updateAddress(firstAddress, actualUser.login, firstAddress.name)
            }*/

        }

        userViewModel.idLiveData.observe(this) {
            Log.e(TAG, "Operation with user ${it}")
            if (it.equals("error")) {
                Log.e(TAG, "sending duplicate item message")
                simulateOnMessage()
            }
        }

        userViewModel.completeLiveData.observe(this) {
            Log.e(TAG, "Operation status: ${it}")

        }

        furnitureModel.furnitureLiveData.observe(this) {
            Log.e(TAG, "=========================================")
            Log.e(TAG, "Furniture id: ${it.id}")
            Log.e(TAG, "Brand: ${it.brand}")
            Log.e(TAG, "Model: ${it.model}")
            Log.e(TAG, "Size: ${it.size}")
            Log.e(TAG, "Style: ${it.style}")
            Log.e(TAG, "Colors: ${it.colors.size}")
            Log.e(TAG, "Dimensions: ${it.getDimensions()}")
            Log.e(TAG, "Short description: ${it.shortDescription}")
            Log.e(TAG, "Long description: ${it.longDescription}")
            Log.e(TAG, "=========================================")
        }

        furnitureModel.idLiveData.observe(this) {
            Log.e(TAG, "Operation with furniture ${it}")
            if (it.equals("error")) {
                Log.e(TAG, "sending duplicate item message")
                simulateOnMessage()
            }
        }

        furnitureModel.completeLiveData.observe(this) {
            Log.e(TAG, "Operation status: ${it}")
        }
    }

    private fun simulateOnMessage() {
        val data = Bundle()
        data.putInt(NotificationService.CMD, NotificationService.CMD_TEST_NOTIFICATION)
        data.putString(NotificationService.KEY_TITLE, "Error")
        data.putString(NotificationService.KEY_MESSAGE, "Duplicated item")
        val intent = Intent(this, NotificationService::class.java)
        intent.putExtras(data)
        startService(intent)
    }

    //------- listening broadcasts from service
    //------- listening broadcasts from service
    /**
     * Listens for service state change broadcasts
     */
    /*private val mServiceStateChangeReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        private val TAG = "BroadcastReceiver"

        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            val data = intent.extras
            Log.d(TAG, "received broadcast message from service: $action")
            if (NotificationService.BROADCAST_ACTION.equals(action)) {
                val title = data?.getString(NotificationService.NOT_TITLE)
                val message = data?.getString(NotificationService.NOT_MESSAGE)
                Log.e(TAG, "Broadcast receiver with $title: $message")
            } else {
                Log.v(TAG, "do nothing for action: $action")
            }
        }
    }


    private fun registerServiceStateChangeReceiver() {
        Log.d(TAG, "registering service state change receiver...")
        val intentFilter = IntentFilter()
        intentFilter.addAction(NotificationService.BROADCAST_ACTION)
        registerReceiver(mServiceStateChangeReceiver, intentFilter)
    }*/
}