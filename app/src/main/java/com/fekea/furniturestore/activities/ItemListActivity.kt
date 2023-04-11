package com.fekea.furniturestore.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fekea.furniturestore.R
import com.fekea.furniturestore.firebase.service.FurnitureModel
import com.fekea.furniturestore.interfaces.ItemClickEvent
import com.fekea.furniturestore.adapters.SmallFurnitureAdapter

class ItemListActivity: AppCompatActivity(), ItemClickEvent {

    private lateinit var furnitureModel: FurnitureModel
    private lateinit var adapter: SmallFurnitureAdapter

    var filterByName = ""
    var filterByCategory = "All"
    var filterByWord = ""

    companion object {
        const val TAG = "com.fekea.furniturestore.ItemListActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        furnitureModel = ViewModelProvider(this)[FurnitureModel::class.java]
        val recycleView = findViewById<RecyclerView>(R.id.item_list_recycle_view)
        recycleView.layoutManager = LinearLayoutManager(this)
        adapter = SmallFurnitureAdapter(this)
        recycleView.adapter = adapter

        setupLiveDataListener()

        furnitureModel.getFurnitureListByFieldTest("category","Table")
    }

    override fun onItemClicked(id: String) {
        Log.e(TAG, "Item $id clicked")
    }

    private fun query() {
        Log.e(TAG, "Starting query with Name: $filterByName Category: $filterByCategory Word: $filterByWord")
    }

    private fun setupLiveDataListener() {
        furnitureModel.furnitureListLiveData.observe(this) {
            Log.e(TAG, "${it.size} elements were recovered")
            adapter.setupAdapterData(it)
        }
    }
}