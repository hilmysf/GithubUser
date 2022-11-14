package com.dicoding.picodiploma.githubuser.ui.viewmodel

import android.app.Application
import android.database.Cursor
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.githubuser.data.User
import com.dicoding.picodiploma.githubuser.data.room.UserDatabase
import com.dicoding.picodiploma.githubuser.data.room.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) :AndroidViewModel(application){

    val readAllData: LiveData<List<User>>
    val readFavoriteData: Cursor
    private val repository: UserRepository

    init{
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository =
            UserRepository(userDao)
        readAllData = repository.readAllData
        readFavoriteData = repository.readFavoriteData
    }

    fun addUser(user:User){
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(user)
        }
    }

    fun deleteUser(user: User){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteUser(user)
        }
    }

    fun deleteAllUser(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllUsers()
        }
    }
}