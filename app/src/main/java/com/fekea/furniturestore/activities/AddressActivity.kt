package com.fekea.furniturestore.activities

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fekea.furniturestore.R
import com.fekea.furniturestore.adapters.AddressAdapter
import com.fekea.furniturestore.firebase.model.DBUserAddress
import com.fekea.furniturestore.firebase.service.UserModel
import com.fekea.furniturestore.interfaces.AddressEdit
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddressActivity: AppCompatActivity(), AddressEdit {

    private lateinit var addressesAdapter: AddressAdapter
    private lateinit var userModel: UserModel

    companion object {
        const val TAG = "com.fekea.furniturestore.AddressActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addresses)

        val recycleView = findViewById<RecyclerView>(R.id.recycle_view_addresses)
        recycleView.layoutManager = LinearLayoutManager(this)
        addressesAdapter = AddressAdapter(this)
        recycleView.adapter = addressesAdapter

        userModel = ViewModelProvider(this)[UserModel::class.java]
        setupLiveDataListener()

        val button = findViewById<FloatingActionButton>(R.id.addresses_button)
        button.setOnClickListener {
            showAddressDialog(DBUserAddress(), true)
        }

        MainActivity.user?.let { addressesAdapter.setupAdapterData(it.addresses) }
    }

    private fun showAddressDialog(address: DBUserAddress, creatingNewAddress: Boolean) {
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        builder.setTitle("Address info")
        val dialogLayout = inflater?.inflate(R.layout.dialog_address, null)
        val textName  = dialogLayout?.findViewById<EditText>(R.id.address_name)
        val textAddress = dialogLayout?.findViewById<EditText>(R.id.address_address)
        val textPostalCode = dialogLayout?.findViewById<EditText>(R.id.address_postal_code)
        val textTelephone = dialogLayout?.findViewById<EditText>(R.id.address_telephone)

        if (!creatingNewAddress) {
            textName?.setText(address.name)
            textAddress?.setText(address.address)
            textPostalCode?.setText(address.postalCode)
            textTelephone?.setText(address.telephone)
        }

        builder.setView(dialogLayout)
        builder.setPositiveButton("Save") { dialogInterface, i ->

            if (creatingNewAddress){
                address.name = textName?.text.toString()
                address.address = textAddress?.text.toString()
                address.postalCode = textPostalCode?.text.toString()
                address.telephone = textTelephone?.text.toString()
                MainActivity.user?.addresses?.add(address)
                MainActivity.user?.let { userModel.updateUser(it) }
            } else {
                val index = MainActivity.user?.addresses?.indexOf(address)
                address.name = textName?.text.toString()
                address.address = textAddress?.text.toString()
                address.postalCode = textPostalCode?.text.toString()
                address.telephone = textTelephone?.text.toString()
                if (index != null) {
                    MainActivity.user?.addresses?.set(index,address)
                    MainActivity.user?.let { userModel.updateUser(it) }
                } else {
                    Log.e(TAG, "Error on update address ${address.name}, index was null")
                }
            }
        }
        builder.setNegativeButton("Cancel") { dialogInterface, i ->
            Log.e(UserFragment.TAG, "Cancel")
        }

        if (!creatingNewAddress) {
            builder.setNeutralButton("Delete") { dialogInterface, i ->
                Log.e(TAG, "Delete address")
                MainActivity.user?.addresses?.remove(address)
                MainActivity.user?.let { userModel.updateUser(it) }
            }
        }

        builder.show()
    }

    override fun onAddressClicked(address: DBUserAddress) {
        showAddressDialog(address, false)
    }

    private fun setupLiveDataListener() {
        userModel.completeLiveData.observe(this) {
            Log.e(TAG, "Was completed successfully: $it")
            addressesAdapter.clearAdapterData()
            MainActivity.user?.let { addressesAdapter.setupAdapterData(it.addresses) }
        }
    }
}