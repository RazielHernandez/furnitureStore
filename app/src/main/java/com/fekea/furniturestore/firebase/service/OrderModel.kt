package com.fekea.furniturestore.firebase.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fekea.furniturestore.activities.MainActivity
import com.fekea.furniturestore.firebase.model.DBOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderModel: ViewModel() {

    private val _orderIdLiveData = MutableLiveData<String>()
    val orderIdLiveData: LiveData<String> = _orderIdLiveData

    fun insertOrder(order: DBOrder) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = MainActivity.db?.insertOrder(order)
            Log.e("TAG", "Inserted with: $data")
            _orderIdLiveData.postValue(data)
        }
    }
}