package com.dicoding.picodiploma.githubuser.data.room

import android.database.Cursor
import androidx.lifecycle.LiveData
import com.dicoding.picodiploma.githubuser.data.User

class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<User>> = userDao.readAllData()
    val readFavoriteData: Cursor = userDao.readFavoriteData()
    fun addUser(user: User) {
        userDao.addUser(user)
    }

//    fun checkUser(username: User){
//        userDao.checkUser(username)
//    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }

}