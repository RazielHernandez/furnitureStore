package com.fekea.furniturestore.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fekea.furniturestore.R
import com.fekea.furniturestore.adapters.FavoriteAdapter
import com.fekea.furniturestore.adapters.SmallFurnitureAdapter
import com.fekea.furniturestore.firebase.service.FurnitureModel
import com.fekea.furniturestore.firebase.service.UserModel
import com.fekea.furniturestore.interfaces.FavoriteRemove
import com.fekea.furniturestore.interfaces.ItemClickEvent

class FavoritesFragment : Fragment(), ItemClickEvent, FavoriteRemove {

    private lateinit var furnitureModel: FurnitureModel
    private lateinit var userModel: UserModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    companion object {
        const val TAG = "com.fekea.furniturestore.FavoritesFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_favorites, container, false)

        userModel = ViewModelProvider(this)[UserModel::class.java]
        furnitureModel = ViewModelProvider(this)[FurnitureModel::class.java]
        setupLiveDataListener()

        val recycleViewSale = v.findViewById<RecyclerView>(R.id.favorites_recycle_view)
        recycleViewSale.layoutManager = LinearLayoutManager(v.context)
        favoriteAdapter = FavoriteAdapter(this, this)
        recycleViewSale.adapter = favoriteAdapter

        MainActivity.user?.favorites?.let { furnitureModel.getFavoriteFurnitureList(it) }

        return v
    }

    override fun onFavoriteRemove(furniture: String) {
        MainActivity.user?.favorites?.remove(furniture)
        MainActivity.user?.let { userModel.updateUser(it) }
    }

    override fun onItemClicked(id: String) {
        val intent = Intent(activity, ItemDetailActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun setupLiveDataListener() {
        userModel.completeLiveData.observe(viewLifecycleOwner) {
            Log.e(AddressActivity.TAG, "Was completed successfully: $it")
            MainActivity.user?.favorites?.let { furnitureModel.getFavoriteFurnitureList(it) }
        }

        furnitureModel.furnitureListLiveData.observe(viewLifecycleOwner) { dbFurnitureList ->
            Log.e(TAG, "Element removed and list reloaded")
            favoriteAdapter.clearAdapterData()
            favoriteAdapter.setupAdapterData(dbFurnitureList)
        }
    }
}