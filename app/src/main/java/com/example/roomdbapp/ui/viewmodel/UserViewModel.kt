package com.example.roomdbapp.ui.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.roomdbapp.model.User
import com.example.roomdbapp.model.UserDataBase
import com.example.roomdbapp.model.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class  UserViewModel(application: Application):AndroidViewModel(application) {
    private val userRepository: UserRepository
    val userLiveData: LiveData<List<User>>
    var searchlivedata: LiveData<List<User>>

    init {
        val userDao= UserDataBase.getDataBase(application).userDao()
        userRepository= UserRepository(userDao)
        userLiveData= userRepository.readAllData
        searchlivedata=userLiveData
    }

    fun addData(user: User){
        viewModelScope.launch (Dispatchers.IO) {
            userRepository.insertAllData(user)
        }
    }

    fun search(query: String) {
        Timber.tag("resp1").d("hydy")
        viewModelScope.launch (Dispatchers.IO) {
            searchlivedata=userRepository.search(query)
        }
    }
    fun update(user: User){
        viewModelScope.launch (Dispatchers.IO) {
            userRepository.update(user)
        }
    }
    fun delete(user: User){
        viewModelScope.launch (Dispatchers.IO) {
            userRepository.delete(user)
        }
    }
    fun deleteAll(){
        viewModelScope.launch (Dispatchers.IO) {
            userRepository.deleteAll()
        }
    }

}