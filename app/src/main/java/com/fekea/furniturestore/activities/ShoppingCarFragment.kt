package com.fekea.furniturestore.activities

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fekea.furniturestore.R
import com.fekea.furniturestore.adapters.FavoriteAdapter
import com.fekea.furniturestore.adapters.ShoppingCarAdapter
import com.fekea.furniturestore.firebase.model.DBFurniture
import com.fekea.furniturestore.firebase.model.DBOrder
import com.fekea.furniturestore.firebase.service.OrderModel
import com.fekea.furniturestore.firebase.service.UserModel
import com.fekea.furniturestore.interfaces.ShoppingCarModifier
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class ShoppingCarFragment : Fragment(), ShoppingCarModifier {

    private lateinit var userModel: UserModel
    private lateinit var orderModel: OrderModel
    private lateinit var shoppingCarAdapter: ShoppingCarAdapter
    private lateinit var v: View

    companion object {
        const val TAG = "com.fekea.furniturestore.ShoppingCarFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_shopping_car, container, false)
        userModel = ViewModelProvider(this)[UserModel::class.java]
        orderModel = ViewModelProvider(this)[OrderModel::class.java]
        setupLiveDataListener()

        if (MainActivity.user?.enable == true) {
            v.findViewById<LinearLayout>(R.id.car_line_logged).visibility = View.VISIBLE
            v.findViewById<TextView>(R.id.car_not_logged).visibility = View.GONE

            val recycleViewCar = v.findViewById<RecyclerView>(R.id.recycle_view_car)
            recycleViewCar.layoutManager = LinearLayoutManager(v.context)
            shoppingCarAdapter = ShoppingCarAdapter(this)
            recycleViewCar.adapter = shoppingCarAdapter

            shoppingCarAdapter.setupAdapterData(MainActivity.user!!.basket)
            showTotal( calculateTotal() )

            v.findViewById<Button>(R.id.car_button_buy).setOnClickListener {
                var newOrder = DBOrder()
                newOrder.user = MainActivity.user!!.login
                val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
                newOrder.orderDate = LocalDate.now().format(formatter)
                newOrder.status = "Pending"
                newOrder.total = calculateTotal()
                newOrder.items = MainActivity.user!!.basket

                orderModel.insertOrder(newOrder)

                showBuyDialog()
            }
        } else {
            v.findViewById<LinearLayout>(R.id.car_line_logged).visibility = View.GONE
            v.findViewById<TextView>(R.id.car_not_logged).visibility = View.VISIBLE
        }
        return v
    }

    private fun showBuyDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = this?.layoutInflater
        builder.setTitle("Buy complete")
        val dialogLayout = inflater?.inflate(R.layout.dialog_item_added, null)
        val textMessage  = dialogLayout?.findViewById<TextView>(R.id.added_message)
        textMessage?.setText("Congratulations! your item will be in your home soon")

        builder.setView(dialogLayout)
        builder.setPositiveButton("Ok") { dialogInterface, i ->
        }
        builder.show()
    }

    override fun onItemIncrease(furniture: DBFurniture) {
        if (furniture.quantity < 10) {
            val index = MainActivity.user?.basket?.indexOf(furniture)
            if (index != null) {
                MainActivity.user?.basket?.get(index)!!.quantity += 1
            }
            MainActivity.user?.let { userModel.updateUser(it) }
        }
    }

    override fun onItemDecrease(furniture: DBFurniture) {
        if (furniture.quantity > 1) {

            val index = MainActivity.user?.basket?.indexOf(furniture)
            if (index != null) {
                MainActivity.user?.basket?.get(index)!!.quantity -= 1
            }
        } else {
            MainActivity.user?.basket?.remove(furniture)
        }
        MainActivity.user?.let { userModel.updateUser(it) }
    }

    private fun showTotal(total: Float) {
        val formatter: NumberFormat = DecimalFormat("$ #,###.##")
        v.findViewById<TextView>(R.id.car_total).text = formatter.format(total)
    }

    private fun calculateTotal(): Float {
        var total = 0F
        val items = MainActivity.user!!.basket
        for (item in items) {
            Log.e(TAG,"Calculating total: ${item.totalPrice()}")
            total += item.totalPrice()
        }
        //val formatter: NumberFormat = DecimalFormat("$ #,###.##")
        //v.findViewById<TextView>(R.id.car_total).text = formatter.format(total)
        return total
    }

    private fun setupLiveDataListener() {
        userModel.completeLiveData.observe(viewLifecycleOwner) {
            Log.e(TAG, "Update ended $it")
            shoppingCarAdapter.cleanAdapter()
            shoppingCarAdapter.setupAdapterData(MainActivity.user!!.basket)
            showTotal( calculateTotal() )
        }

        orderModel.orderIdLiveData.observe(viewLifecycleOwner) {
            Log.e(TAG, "Insert order ended $it")
            MainActivity.user?.basket?.clear()
            MainActivity.user?.let { it1 -> userModel.updateUser(it1) }
        }
    }
}