package com.fekea.furniturestore.firebase.service

import android.content.Context
import android.net.http.SslCertificate.DName
import android.util.Log
import com.fekea.furniturestore.firebase.model.*
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.log

class FurnitureDatabase (appContext: Context) {

    val db = Firebase.firestore

    companion object{
        const val TAG = "com.fekea.furniturestore.FurnitureDatabase"
        const val COLLECTION_USERS = "users_collection"
        const val COLLECTION_FURNITURE = "furniture_collection"
        const val COLLECTION_STORES = "stores_collection"
        const val COLLECTION_ORDERS = "orders_collection"
    }

    suspend fun insertUser(user: DBUser): String {
        var result: String = "error"
        withContext(Dispatchers.IO){
            if (!userExists(user.login)) {
                db.collection(COLLECTION_USERS)
                    .document(user.login)
                    .set(user.modelToHashMapOf())
                    .addOnSuccessListener {
                        Log.e(TAG, "User added successfully: ${user.login}")
                        result = user.login
                    }
                    .addOnFailureListener {
                        Log.e(TAG, "Error on add new user")
                    }
                    .await()
            }else {
                Log.e(TAG, "User ${user.login} already exists")
            }
        }
        Log.e(TAG,"Inserted with $result")
        return result
    }

    suspend fun userExists(login: String): Boolean {
        var result = false
        withContext(Dispatchers.IO) {
            db.collection(COLLECTION_USERS).document(login)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        result = true
                        Log.e(TAG, "User found: $document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error on verify document: $login ", exception)
                }
                .await()
        }
        return result
    }

    suspend fun updateUser(user: DBUser): Boolean {
        var result = false
        withContext(Dispatchers.IO) {
            db.collection(COLLECTION_USERS).document(user.login)
                .update(user.modelToHashMapOf())
                .addOnSuccessListener {
                    Log.e(TAG, "Update user was completed successfully (${user.login})")
                    result = true
                }
                .addOnFailureListener {
                    Log.e(
                        TAG,
                        "Error on Insert user (${user.login}) : ${it.localizedMessage}"
                    )
                }
                .await()
        }

        return result
    }

    suspend fun deleteUser(user: String): Boolean {
        var result = false
        withContext(Dispatchers.IO) {
            db.collection(COLLECTION_USERS).document(user)
                .delete()
                .addOnSuccessListener {
                    Log.e(TAG, "Delete user was completed successfully (${user})")
                    result = true
                }
                .addOnFailureListener {
                    Log.e(TAG, "Error on delete user (${user}) : ${it.localizedMessage}")
                }
                .await()
        }
        return result
    }

    suspend fun getUser(login: String): DBUser {
        val result = suspendCoroutine<DBUser> { cont ->
            db.collection(COLLECTION_USERS).document(login)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()){
                        Log.e(TAG, "Found: $document")
                        var recovered = document.toObject<DBUser>()!!
                        cont.resume(recovered)
                    } else {
                        Log.e(TAG, "Not found, creating a null user")
                        val userNotFound = DBUser()
                        userNotFound.type = "NULL"
                        cont.resume(userNotFound)
                    }
                }
                .addOnFailureListener {
                    Log.e(TAG, "Error on get user with id ${login}. Exception: $it")
                }
        }
        Log.e(TAG,"database result: $result")
        return result
    }

    suspend fun insertFurniture(furniture: DBFurniture): String {
        var result: String = "error"
        withContext(Dispatchers.IO){
            if (!furnitureExists(furniture.id)) {
                db.collection(COLLECTION_FURNITURE)
                    .document(furniture.id)
                    .set(furniture.modelToHashMapOf())
                    .addOnSuccessListener {
                        Log.e(TAG, "Furniture added successfully: ${furniture.id}")
                        result = furniture.id
                    }
                    .addOnFailureListener {
                        Log.e(TAG, "Error on add new furniture")
                    }
                    .await()
            }else {
                Log.e(TAG, "Furniture ${furniture.id} already exists")
            }
        }
        return result
    }

    suspend fun furnitureExists(id: String): Boolean {
        var result = false
        withContext(Dispatchers.IO) {
            db.collection(COLLECTION_FURNITURE).document(id)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        result = true
                        Log.e(TAG, "Furniture found: $document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error on verify Furniture: $id ", exception)
                }
                .await()
        }
        return result
    }

    suspend fun updateFurniture(furniture: DBFurniture): Boolean {
        var result = false
        withContext(Dispatchers.IO) {
            db.collection(COLLECTION_FURNITURE).document(furniture.id)
                .update(furniture.modelToHashMapOf())
                .addOnSuccessListener {
                    Log.e(TAG, "Update furniture was completed successfully (${furniture.id})")
                    result = true
                }
                .addOnFailureListener {
                    Log.e(
                        TAG,
                        "Error on Insert furniture (${furniture.id}) : ${it.localizedMessage}"
                    )
                }
                .await()
        }

        return result
    }

    suspend fun deleteFurniture(furniture: String): Boolean {
        var result = false
        withContext(Dispatchers.IO) {
            db.collection(COLLECTION_FURNITURE).document(furniture)
                .delete()
                .addOnSuccessListener {
                    Log.e(TAG, "Delete furniture was completed successfully (${furniture})")
                    result = true
                }
                .addOnFailureListener {
                    Log.e(TAG, "Error on delete furniture (${furniture}) : ${it.localizedMessage}")
                }
                .await()
        }
        return result
    }

    suspend fun getFurniture(id: String): DBFurniture {
        val result = suspendCoroutine<DBFurniture> { cont ->
            db.collection(COLLECTION_FURNITURE).document(id)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()){
                        Log.e(TAG, "Found: $document")
                        var recovered = document.toObject<DBFurniture>()!!
                        cont.resume(recovered)
                    }
                }
                .addOnFailureListener {
                    Log.e(TAG, "Error on get furniture with id ${id}. Exception: $it")
                }
        }

        return result
    }

    suspend fun getFurnitureListByField(field: String, query: String): List<DBFurniture> {
        var result = mutableListOf<DBFurniture>()
        db.collection(COLLECTION_FURNITURE)
            .whereEqualTo(field, query)
            .get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                Log.e(TAG, "List founded ${queryDocumentSnapshots.size()}")
                result.addAll(queryDocumentSnapshots.toObjects<DBFurniture>())
            }
            .addOnFailureListener {
                Log.e(TAG, "Error on get furniture list with field $field and query $query. Exception: $it")
            }
            .await()
        return result
    }

    suspend fun getFurnitureListByFieldTest(field: String, query: String): List<DBFurniture> {
        var result = mutableListOf<DBFurniture>()
        db.collection(COLLECTION_FURNITURE)
            .whereGreaterThanOrEqualTo(field, query)
            .whereLessThanOrEqualTo(field,query)
            .get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                Log.e(TAG, "List founded ${queryDocumentSnapshots.size()}")
                result.addAll(queryDocumentSnapshots.toObjects<DBFurniture>())
            }
            .addOnFailureListener {
                Log.e(TAG, "Error on get furniture list with field $field and query $query. Exception: $it")
            }
            .await()
        return result
    }

    suspend fun getFurnitureList(): List<DBFurniture> {
        var result = mutableListOf<DBFurniture>()
        db.collection(COLLECTION_FURNITURE)
            .get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                Log.e(TAG, "List founded ${queryDocumentSnapshots.size()}")
                for (snapshot in queryDocumentSnapshots) {
                    var actual = snapshot.toObject<DBFurniture>()
                    actual.id = snapshot.id
                    result.add(actual)
                }
                //result.addAll(queryDocumentSnapshots.toObjects<DBFurniture>())
            }
            .addOnFailureListener {
                Log.e(TAG, "Error on get furniture list. Exception: $it")
            }
            .await()
        return result
    }

    suspend fun getFurnitureListByIds(ids: List<String>): List<DBFurniture> {
        var result = mutableListOf<DBFurniture>()
        db.collection(COLLECTION_FURNITURE)
            .get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                Log.e(TAG, "List founded ${queryDocumentSnapshots.size()}")
                for (snapshot in queryDocumentSnapshots) {
                    if(ids.contains(snapshot.id)){
                        var actual = snapshot.toObject<DBFurniture>()
                        actual.id = snapshot.id
                        result.add(actual)
                    }
                }
            }
            .addOnFailureListener {
                Log.e(TAG, "Error on get furniture list. Exception: $it")
            }
            .await()
        return result
    }

    suspend fun getStoresByDocumentName(ids: List<String>): List<DBStore> {
        var result = mutableListOf<DBStore>()
        db.collection(COLLECTION_STORES)
            .get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                for (snapshot in queryDocumentSnapshots) {
                    if (ids.contains(snapshot.id)){
                        Log.e(TAG, "Store founded ${snapshot.id}")
                        var actual = snapshot.toObject<DBStore>()
                        result.add(actual)
                    }
                }
            }
            .addOnFailureListener {
                Log.e(TAG, "Error on get store list. Exception: $it")
            }
            .await()
        return result
    }

    suspend fun insertOrder(order: DBOrder): String {
        var result: String = "error"
        withContext(Dispatchers.IO){
            db.collection(COLLECTION_ORDERS)
                .add(order.modelToHashMapOf())
                .addOnSuccessListener {
                    Log.e(TAG, "Order added successfully: ${order.user} - ${order.orderDate}")
                    result = order.user + "-" + order.orderDate
                }
                .addOnFailureListener {
                    Log.e(TAG, "Error on add new order")
                }
                .await()
        }
        return result
    }

}