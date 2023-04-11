package com.fekea.furniturestore.interfaces

import com.fekea.furniturestore.firebase.model.DBUserCreditCard

interface CreditcardEdit {
    fun onCreditCardClicked(card: DBUserCreditCard)
}