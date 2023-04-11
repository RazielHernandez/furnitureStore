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
import com.fekea.furniturestore.adapters.CreditCardAdapter
import com.fekea.furniturestore.firebase.model.DBUserAddress
import com.fekea.furniturestore.firebase.model.DBUserCreditCard
import com.fekea.furniturestore.firebase.service.UserModel
import com.fekea.furniturestore.interfaces.CreditcardEdit
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CreditCardActivity: AppCompatActivity(), CreditcardEdit {

    private lateinit var creditCardAdapter: CreditCardAdapter
    private lateinit var userModel: UserModel

    companion object {
        const val TAG = "com.fekea.furniturestore.CreditCardActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creditcards)

        val recycleView = findViewById<RecyclerView>(R.id.recycle_view_credit_cards)
        recycleView.layoutManager = LinearLayoutManager(this)
        creditCardAdapter = CreditCardAdapter(this)
        recycleView.adapter = creditCardAdapter

        userModel = ViewModelProvider(this)[UserModel::class.java]
        setupLiveDataListener()

        val button = findViewById<FloatingActionButton>(R.id.creditcards_button)
        button.setOnClickListener {
            showCreditCardDialog(DBUserCreditCard(), true)
        }

        MainActivity.user?.let { creditCardAdapter.setupAdapterData(it.creditCards) }
    }

    private fun showCreditCardDialog(card: DBUserCreditCard, creatingNewCard: Boolean) {
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        builder.setTitle("Credit card info")
        val dialogLayout = inflater?.inflate(R.layout.dialog_card, null)
        val textName  = dialogLayout?.findViewById<EditText>(R.id.card_name)
        val textNumber = dialogLayout?.findViewById<EditText>(R.id.card_number)
        val textExpireDate = dialogLayout?.findViewById<EditText>(R.id.card_expire_date)
        val textCVV = dialogLayout?.findViewById<EditText>(R.id.card_cvv)

        if (!creatingNewCard) {
            textName?.setText(card.name)
            textNumber?.setText(card.number)
            textExpireDate?.setText(card.expireDate)
            textCVV?.setText(card.cvv)
        }

        builder.setView(dialogLayout)
        builder.setPositiveButton("Save") { dialogInterface, i ->

            if (creatingNewCard){
                card.name = textName?.text.toString()
                card.number = textNumber?.text.toString()
                card.expireDate = textExpireDate?.text.toString()
                card.cvv = textCVV?.text.toString()
                MainActivity.user?.creditCards?.add(card)
                MainActivity.user?.let { userModel.updateUser(it) }
            } else {
                val index = MainActivity.user?.creditCards?.indexOf(card)
                card.name = textName?.text.toString()
                card.number = textNumber?.text.toString()
                card.expireDate = textExpireDate?.text.toString()
                card.cvv = textCVV?.text.toString()
                if (index != null) {
                    MainActivity.user?.creditCards?.set(index,card)
                    MainActivity.user?.let { userModel.updateUser(it) }
                } else {
                    Log.e(AddressActivity.TAG, "Error on update card ${card.name}, index was null")
                }
            }
        }
        builder.setNegativeButton("Cancel") { dialogInterface, i ->
            Log.e(UserFragment.TAG, "Cancel")
        }

        if (!creatingNewCard) {
            builder.setNeutralButton("Delete") { dialogInterface, i ->
                Log.e(AddressActivity.TAG, "Delete card")
                MainActivity.user?.creditCards?.remove(card)
                MainActivity.user?.let { userModel.updateUser(it) }
            }
        }

        builder.show()
    }

    override fun onCreditCardClicked(card: DBUserCreditCard) {
        showCreditCardDialog(card, false)
    }

    private fun setupLiveDataListener() {
        userModel.completeLiveData.observe(this) {
            Log.e(AddressActivity.TAG, "Was completed successfully: $it")
            creditCardAdapter.clearAdapterData()
            MainActivity.user?.let { creditCardAdapter.setupAdapterData(it.creditCards) }
        }
    }
}