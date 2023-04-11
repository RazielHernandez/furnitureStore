package com.fekea.furniturestore.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.fekea.furniturestore.R

class ARActivity: AppCompatActivity(R.layout.activity_ar) {

    companion object {
        const val TAG = "com.fekea.furniturestore.ARActivity"
        var modelName: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_ar)
        modelName = intent.getStringExtra("modelName").toString()

        supportFragmentManager.commit {
            add(R.id.containerFragment, ARFragment::class.java, Bundle())
        }

    }
}