package com.fekea.furniturestore.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fekea.furniturestore.R
import com.fekea.furniturestore.firebase.model.DBUser
import com.fekea.furniturestore.firebase.service.FurnitureDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView

    companion object {
        const val TAG = "com.fekea.furniturestore.activities.MainActivity"
        var db: FurnitureDatabase? = null
        var user: DBUser? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(HomeFragment())

        db = FurnitureDatabase(applicationContext)

        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.favorites -> {
                    loadFragment(FavoritesFragment())
                    true
                }
                R.id.shopping_car -> {
                    loadFragment(ShoppingCarFragment())
                    true
                }
                R.id.settings -> {
                    loadFragment(UserFragment())
                    true
                }
                else -> {
                    true
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

    override fun onBackPressed() {}

}

