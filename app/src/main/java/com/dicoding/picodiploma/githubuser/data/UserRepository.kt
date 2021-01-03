package com.dicoding.picodiploma.githubuser.data

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<User>> = userDao.readAllData()

    fun addUser(user:User){
        userDao.addUser(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }
    suspend fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }
}