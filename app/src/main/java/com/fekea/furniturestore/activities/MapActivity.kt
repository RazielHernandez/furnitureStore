package com.fekea.furniturestore.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.fekea.furniturestore.R
import com.fekea.furniturestore.firebase.model.DBStore
import com.fekea.furniturestore.firebase.service.FurnitureModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity: AppCompatActivity(), OnMapReadyCallback {

    private lateinit var furnitureModel: FurnitureModel
    var stores: List<DBStore> = listOf()
    var item: String = ""

    companion object{
        const val TAG = "centennialcollege.ca.besttrip.activities:RouteActivity"
    }

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        furnitureModel = ViewModelProvider(this)[FurnitureModel::class.java]
        val storeIds = intent.getStringArrayListExtra("stores")
        item = intent.getStringExtra("item").toString()

        if (storeIds != null) {
            furnitureModel.getStoresByIds(storeIds.toList())
            furnitureModel.storesLiveData.observe(this) {
                stores = it
                displayStores()
            }
        }
    }

    private fun displayStores() {
        for (store in stores) {
            val position = LatLng(store.latitude, store.longitude)
            val storeName = store.name
            mMap.addMarker(
                MarkerOptions()
                    .position(position)
                    .title("Store $storeName"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(position))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings
    }
}