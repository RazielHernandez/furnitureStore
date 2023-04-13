package com.fekea.furniturestore.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
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

        filterByCategory = intent.getStringExtra("category").toString()

        onCategoryClicked()

        findViewById<TextView>(R.id.search_category_all).setOnClickListener {
            filterByCategory = findViewById<TextView>(R.id.search_category_all).text.toString()
            onCategoryClicked()
        }

        findViewById<TextView>(R.id.search_category_bedroom).setOnClickListener {
            filterByCategory = findViewById<TextView>(R.id.search_category_bedroom).text.toString()
            onCategoryClicked()
        }

        findViewById<TextView>(R.id.search_category_kitchen).setOnClickListener {
            filterByCategory = findViewById<TextView>(R.id.search_category_kitchen).text.toString()
            onCategoryClicked()
        }

        findViewById<TextView>(R.id.search_category_living).setOnClickListener {
            filterByCategory = findViewById<TextView>(R.id.search_category_living).text.toString()
            onCategoryClicked()
        }

        findViewById<TextView>(R.id.search_category_bathroom).setOnClickListener {
            filterByCategory = findViewById<TextView>(R.id.search_category_bathroom).text.toString()
            onCategoryClicked()
        }

        findViewById<TextView>(R.id.search_category_garden).setOnClickListener {
            filterByCategory = findViewById<TextView>(R.id.search_category_garden).text.toString()
            onCategoryClicked()
        }

        findViewById<TextView>(R.id.search_category_chair).setOnClickListener {
            filterByCategory = findViewById<TextView>(R.id.search_category_chair).text.toString()
            onCategoryClicked()
        }

        findViewById<TextView>(R.id.search_category_lamp).setOnClickListener {
            filterByCategory = findViewById<TextView>(R.id.search_category_lamp).text.toString()
            onCategoryClicked()
        }

        findViewById<TextView>(R.id.search_category_sofa).setOnClickListener {
            filterByCategory = findViewById<TextView>(R.id.search_category_sofa).text.toString()
            onCategoryClicked()
        }

        findViewById<TextView>(R.id.search_category_table).setOnClickListener {
            filterByCategory = findViewById<TextView>(R.id.search_category_table).text.toString()
            onCategoryClicked()
        }

        findViewById<TextView>(R.id.search_category_cabinet).setOnClickListener {
            filterByCategory = findViewById<TextView>(R.id.search_category_cabinet).text.toString()
            onCategoryClicked()
        }
    }

    fun onCategoryClicked() {
        if (filterByCategory.equals("All")) {
            furnitureModel.getFurnitureList()
        }else {
            furnitureModel.getFurnitureListByWord(filterByCategory)
        }
    }

    override fun onItemClicked(id: String) {
        Log.e(TAG, "Item $id clicked")
        val intent = Intent(this, ItemDetailActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun query() {
        Log.e(TAG, "Starting query with Category: $filterByCategory Word: $filterByWord")
    }

    private fun setupLiveDataListener() {
        furnitureModel.furnitureListLiveData.observe(this) {
            Log.e(TAG, "${it.size} elements were recovered")
            adapter.setupAdapterData(it)
        }
    }
}