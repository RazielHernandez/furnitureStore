package com.fekea.furniturestore.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fekea.furniturestore.R
import com.fekea.furniturestore.firebase.model.DBFurniture
import com.fekea.furniturestore.firebase.service.FurnitureModel
import com.fekea.furniturestore.interfaces.ItemClickEvent
import com.fekea.furniturestore.adapters.SmallFurnitureAdapter


class HomeFragment : Fragment(), ItemClickEvent {

    private lateinit var furnitureModel: FurnitureModel
    private lateinit var adapterForBest: SmallFurnitureAdapter
    private lateinit var adapterForSales: SmallFurnitureAdapter
    private lateinit var listOfFurnitures: MutableList<DBFurniture>
    private lateinit var itemsOnSale: MutableList<DBFurniture>
    private lateinit var itemsOnBest: MutableList<DBFurniture>
    private lateinit var searchView: SearchView

    companion object {
        const val TAG = "com.fekea.furniturestore.ItemListActivity"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_home, container, false)
        furnitureModel = ViewModelProvider(this)[FurnitureModel::class.java]

        val recycleViewSale = v.findViewById<RecyclerView>(R.id.item_sale_recycle_view)
        recycleViewSale.layoutManager = LinearLayoutManager(v.context, LinearLayoutManager.HORIZONTAL, false)
        adapterForSales = SmallFurnitureAdapter(this)
        recycleViewSale.adapter = adapterForSales

        val recycleViewBest = v.findViewById<RecyclerView>(R.id.item_best_recycle_view)
        recycleViewBest.layoutManager = LinearLayoutManager(v.context, LinearLayoutManager.HORIZONTAL, false)
        adapterForBest = SmallFurnitureAdapter(this)
        recycleViewBest.adapter = adapterForBest

        searchView = v.findViewById<SearchView> (R.id.home_search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.e(TAG,"onQueryTextSubmit")
                val intent = Intent(activity, ItemListActivity::class.java)
                intent.putExtra("category",query)
                startActivity(intent)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e(TAG, "onQueryTextChange")
                return false
            }
        })

        v.findViewById<TextView>(R.id.search_category_all).setOnClickListener {
            onCategoryClicked(v.findViewById<TextView>(R.id.search_category_all).text.toString())
        }

        v.findViewById<TextView>(R.id.search_category_bedroom).setOnClickListener {
            onCategoryClicked(v.findViewById<TextView>(R.id.search_category_bedroom).text.toString())
        }

        v.findViewById<TextView>(R.id.search_category_kitchen).setOnClickListener {
            onCategoryClicked(v.findViewById<TextView>(R.id.search_category_kitchen).text.toString())
        }

        v.findViewById<TextView>(R.id.search_category_kitchen).setOnClickListener {
            onCategoryClicked(v.findViewById<TextView>(R.id.search_category_kitchen).text.toString())
        }

        v.findViewById<TextView>(R.id.search_category_living).setOnClickListener {
            onCategoryClicked(v.findViewById<TextView>(R.id.search_category_living).text.toString())
        }

        v.findViewById<TextView>(R.id.search_category_bathroom).setOnClickListener {
            onCategoryClicked(v.findViewById<TextView>(R.id.search_category_bathroom).text.toString())
        }

        v.findViewById<TextView>(R.id.search_category_garden).setOnClickListener {
            onCategoryClicked(v.findViewById<TextView>(R.id.search_category_garden).text.toString())
        }

        v.findViewById<TextView>(R.id.search_category_chair).setOnClickListener {
            onCategoryClicked(v.findViewById<TextView>(R.id.search_category_chair).text.toString())
        }

        v.findViewById<TextView>(R.id.search_category_lamp).setOnClickListener {
            onCategoryClicked(v.findViewById<TextView>(R.id.search_category_lamp).text.toString())
        }

        v.findViewById<TextView>(R.id.search_category_sofa).setOnClickListener {
            onCategoryClicked(v.findViewById<TextView>(R.id.search_category_sofa).text.toString())
        }

        v.findViewById<TextView>(R.id.search_category_table).setOnClickListener {
            onCategoryClicked(v.findViewById<TextView>(R.id.search_category_table).text.toString())
        }

        v.findViewById<TextView>(R.id.search_category_cabinet).setOnClickListener {
            onCategoryClicked(v.findViewById<TextView>(R.id.search_category_cabinet).text.toString())
        }

        setupLiveDataListener()

        furnitureModel.getFurnitureList()
        return v
    }

    fun onCategoryClicked(category: String) {
        val intent = Intent(activity, ItemListActivity::class.java)
        intent.putExtra("category",category)
        startActivity(intent)
    }

    override fun onItemClicked(id: String) {
        val intent = Intent(activity, ItemDetailActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun setupLiveDataListener() {

        listOfFurnitures = mutableListOf()
        itemsOnSale = mutableListOf()
        itemsOnBest = mutableListOf()

        furnitureModel.furnitureListLiveData.observe(viewLifecycleOwner) { dbFurnitureList ->
            Log.e(ItemListActivity.TAG, "${dbFurnitureList.size} elements were recovered")

            listOfFurnitures.addAll(dbFurnitureList)


            for (item in listOfFurnitures) {
                if (item.rating >= 4.5F){
                    itemsOnBest.add(item)
                }

                if (item.discount > 0){
                    itemsOnSale.add(item)
                }
            }

            adapterForSales.setupAdapterData(itemsOnSale)
            adapterForBest.setupAdapterData(itemsOnBest)

            /*
            var result = dbFurnitureList.filter {
                it.searchWord("Bed")
            }

            result.sortedByDescending { resulted ->
                resulted.sortByWord("bed")
            }
            adapter.setupAdapterData(result)*/
        }
    }


}