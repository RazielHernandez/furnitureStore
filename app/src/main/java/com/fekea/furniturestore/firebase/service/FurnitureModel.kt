package com.fekea.furniturestore.firebase.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fekea.furniturestore.activities.DatabaseActivity
import com.fekea.furniturestore.activities.MainActivity
import com.fekea.furniturestore.firebase.model.DBFurniture
import com.fekea.furniturestore.firebase.model.DBStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FurnitureModel: ViewModel() {
    private val _furnitureListLiveData = MutableLiveData<List<DBFurniture>>()
    val furnitureListLiveData: LiveData<List<DBFurniture>> = _furnitureListLiveData

    private val _furnitureLiveData = MutableLiveData<DBFurniture>()
    val furnitureLiveData: LiveData<DBFurniture> = _furnitureLiveData

    private val _idLiveData = MutableLiveData<String> ()
    val idLiveData: LiveData<String> = _idLiveData

    private val _completeLiveData = MutableLiveData<Boolean> ()
    val completeLiveData: LiveData<Boolean> = _completeLiveData

    private val _storesLiveData = MutableLiveData<List<DBStore>> ()
    val storesLiveData: LiveData<List<DBStore>> = _storesLiveData

    fun insertFurniture(furniture: DBFurniture) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = MainActivity.db?.insertFurniture(furniture)
            Log.e("TAG", "Inserted with: $data")
            _idLiveData.postValue(data)
        }
    }

    fun updateFurniture(furniture: DBFurniture) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = MainActivity.db?.updateFurniture(furniture)
            Log.e("TAG", "Updated with: $data")
            _completeLiveData.postValue(data)
        }
    }

    fun deleteFurniture(furniture: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = MainActivity.db?.deleteFurniture(furniture)
            Log.e("TAG", "Deleted with: $data")
            _completeLiveData.postValue(data)
        }
    }

    fun getFurnitureByLogin(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = MainActivity.db?.getFurniture(id)
            Log.e("TAG", "Furniture with: $data")
            _furnitureLiveData.postValue(data)
        }
    }

    fun getFurnitureListByField(field: String, query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = MainActivity.db?.getFurnitureListByField(field, query)
            Log.e("TAG", "List of furniture: $data")
            _furnitureListLiveData.postValue(data)
        }
    }

    fun getFurnitureListByFieldTest(field: String, query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = MainActivity.db?.getFurnitureListByFieldTest(field, query)
            Log.e("TAG", "List of furniture: $data")
            _furnitureListLiveData.postValue(data)
        }
    }

    fun getFurnitureList() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = MainActivity.db?.getFurnitureList()
            Log.e("TAG", "List of furniture: $data")
            _furnitureListLiveData.postValue(data)
        }
    }

    fun getFavoriteFurnitureList(favorites: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = MainActivity.db?.getFurnitureListByIds(favorites)
            Log.e("TAG", "List of favorite furniture: $data")
            _furnitureListLiveData.postValue(data)
        }
    }

    fun getStoresByIds(ids: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = MainActivity.db?.getStoresByDocumentName(ids)
            Log.e("TAG", "List of stores: $data")
            _storesLiveData.postValue(data)
        }
    }
}