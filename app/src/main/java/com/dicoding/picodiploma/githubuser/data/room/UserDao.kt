package com.dicoding.picodiploma.githubuser.data.room

import android.content.ContentValues
import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.picodiploma.githubuser.data.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

//    @Query("SELECT * FROM user_table WHERE login = :username")
//    fun checkUser(username: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readData(): LiveData<List<User>>

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readFavoriteData(): Cursor

    fun insert(values: ContentValues?): Long
}