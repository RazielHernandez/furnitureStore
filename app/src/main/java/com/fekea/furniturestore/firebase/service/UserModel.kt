package com.fekea.furniturestore.firebase.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fekea.furniturestore.activities.DatabaseActivity
import com.fekea.furniturestore.activities.MainActivity
import com.fekea.furniturestore.firebase.model.DBUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserModel: ViewModel() {

    private val _userLiveData = MutableLiveData<DBUser>()
    val userLiveData: LiveData<DBUser> = _userLiveData

    private val _idLiveData = MutableLiveData<String> ()
    val idLiveData: LiveData<String> = _idLiveData

    private val _completeLiveData = MutableLiveData<Boolean> ()
    val completeLiveData: LiveData<Boolean> = _completeLiveData

    private val _userLogged = MutableLiveData<Boolean> ()
    val userLogged: LiveData<Boolean> = _userLogged

    fun insertUser(user: DBUser) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = MainActivity.db?.insertUser(user)
            Log.e("TAG", "Inserted with: $data")
            if (data != null) {
                _idLiveData.postValue(data)
                val user = MainActivity.db?.getUser(data)
                _userLiveData.postValue(user)
            }
        }
    }

    fun updateUser(user: DBUser) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = MainActivity.db?.updateUser( user)
            Log.e("TAG", "Updated with: $data")
            _completeLiveData.postValue(data)
        }
    }

    fun deleteUser(user: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = MainActivity.db?.deleteUser(user)
            Log.e("TAG", "Deleted with: $data")
            _completeLiveData.postValue(data)
        }
    }


    fun getUserByLogin(login: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = MainActivity.db?.getUser(login)
            Log.e("TAG", "User with: $data")
            _userLiveData.postValue(data)
        }
    }

    fun loginUser (user: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var result = false
            val data = MainActivity.db?.getUser(user)
            if (data != null) {
                Log.e("TAG", "User founded, comparing password ${data.password} / $password")
                if (data.password == password){
                    Log.e("TAG", "Putting data ${data.enable}")
                    _userLiveData.postValue(data)
                    result = true
                }
            }
            Log.e("TAG", "User logged: $result")
            _userLogged.postValue(result)
        }
    }

}